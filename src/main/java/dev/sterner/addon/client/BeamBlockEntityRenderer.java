package dev.sterner.addon.client;

import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.sterner.addon.AddonClient;
import dev.sterner.addon.common.BeamBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Matrix4f;
import org.joml.Vector2d;
import org.joml.Vector4f;

public class BeamBlockEntityRenderer implements BlockEntityRenderer<BeamBlockEntity> {
	public BeamBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
	}

	@Override
	public void render(BeamBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		if(entity.getWorld() == null){
			return;
		}
		float f = (float)entity.getWorld().getTime() + tickDelta;
		int color = Integer.parseInt(Integer.toString(0x7ecdfb, 16), 16);
		final int red = color >> 16;
		final int green = color >> 8;
		final int blue = color;

		Vec3d origin = Vec3d.ZERO;
		Vec3d target = new Vec3d(5,0,0);


		World world = entity.getWorld();
		//List<Vec3d> list = new ArrayList<>();
		//list.add(origin);
		//var v = new Xoroshiro128PlusPlusRandom(123123L);
		//list.add(new Vec3d(0,MathHelper.sin(f * 0.1f) + 1,0));
/*
		double distance = origin.distanceTo(target);
		int steps = 8;
		float normalized = 0;
		double stepLength = distance / steps;
		for(int i = 1; i < steps; ++i){
			normalized += 1f / ((float)steps);

			float strength = MathHelper.sin(normalized);
			list.add(new Vec3d(stepLength * i, MathHelper.sin(f * 0.1f) * strength,0));
		}


 */
		/*
		builder.vertex(matrix, -halfWidth, 0, 0).color(r, g, b, a).uv(0.0F, 1.0F).overlay(overlay).light(light).normal(0.0F, 1.0F, 0.0F).next();
		builder.vertex(matrix, halfWidth, 0, 0).color(r, g, b, a).uv(1.0F, 1.0F).overlay(overlay).light(light).normal(0.0F, 1.0F, 0.0F).next();
		builder.vertex(matrix, halfWidth, 0, distance).color(r, g, b, a).uv(1.0F, 0.0F).overlay(overlay).light(light).normal(0.0F, 1.0F, 0.0F).next();
		builder.vertex(matrix, -halfWidth, 0, distance).color(r, g, b, a).uv(0.0F, 0.0F).overlay(overlay).light(light).normal(0.0F, 1.0F, 0.0F).next();
		 */
		//list.add(target);
		VertexConsumer builder = vertexConsumers.getBuffer(AddonClient.getEnergySwirl(0,  f * 0.01F % 1.0F));
		Vec3d start = Vec3d.ZERO;
		Vec3d end = new Vec3d(0,2,3);
		matrices.push();
		int steps = (int)end.subtract(start).length() * 10;

		if (steps > 40) steps = 40;
		Vector2d angle = getAngle(end.subtract(start));
		Matrix4f matrix4f = matrices.peek().getModel();
		Vec3d lastInterp = start;
		Vec3d lastUp = new Vec3d(0, 1, 0);
		float time = tickDelta + world.getTime();

		float scroll = time / 10;
		float minX = scroll % 1;
		float maxX = minX + 1;

		time /= 30;

		Matrix4f cameraMatrix = new Matrix4f();
		cameraMatrix.rotate(MinecraftClient.getInstance().gameRenderer.getCamera().getRotation());// 100

		for (int i = 0; i < steps; i++) {
			double delta = i / (double) steps;
			double amp = Math.min(delta, 1 - delta);
			amp *= 1.1;
			Vec3d interp = interp(delta, start, end);
			float dScl = 30;

			// TODO: correct texture offsets
			Vec3d offset = new Vec3d(
					MathHelper.cos((float)(delta * dScl) + time) * amp,
					MathHelper.sin((float)(delta * dScl) + time) * amp,
					MathHelper.cos((float)(delta * dScl * 2) + time * 1.2f) * amp / 5
			);

			interp = interp.add(rotate(offset, angle));

			Vector2d rot = new Vector2d(
					MathHelper.sin((float)(delta * dScl) + time) * amp,
					MathHelper.cos((float)(delta * dScl) + time) * amp
			);
			Vector4f upV4 = new Vector4f(0, 0.5f, 0, 1);
			upV4.mul(cameraMatrix);
			Vec3d up = new Vec3d(upV4.x, upV4.y, upV4.z);
			up = rotate(rotate(up, angle), rot);

			if (i != 0) {
				builder.vertex(matrix4f, (float)(lastInterp.x + lastUp.x), (float)(lastInterp.y + lastUp.y), (float)(lastInterp.z + lastUp.z)).color(red, green, blue, 255).uv(1, minX).overlay(overlay).light(light).normal(0,1,0).next();
				builder.vertex(matrix4f, (float)(interp.x + up.x), (float)(interp.y + up.y), (float)(interp.z + up.z)).color(red, green, blue, 255).uv(1, maxX).overlay(overlay).light(light).normal(0,1,0).next();
				builder.vertex(matrix4f, (float)(interp.x - up.x), (float)(interp.y - up.y), (float)(interp.z - up.z)).color(red, green, blue, 255).uv(0, maxX).overlay(overlay).light(light).normal(0,1,0).next();
				builder.vertex(matrix4f, (float)(lastInterp.x - lastUp.x), (float)(lastInterp.y - lastUp.y), (float)(lastInterp.z - lastUp.z)).color(red, green, blue, 255).uv(0, minX).overlay(overlay).light(light).normal(0,1,0).next();
			}

			lastInterp = interp;
			lastUp = up;
		}
		matrices.pop();
/*
		matrices.push();
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		//RenderHelper.drawSteppedLineBetween(vertexConsumers, matrices, list, 0.5F, red, green, blue, 255, f * 0.01F % 1.0F, light, overlay);
		RenderSystem.depthMask(true);
		RenderSystem.disableBlend();
		matrices.pop();

 */
	}

	@Override
	public boolean rendersOutsideBoundingBox(BeamBlockEntity blockEntity) {
		return true;
	}

	@Override
	public int getRenderDistance() {
		return 256;
	}

	Vec3d rotate(Vec3d src, Vector2d rotation) {
		return src.rotateX((float)rotation.x).rotateY((float)rotation.y);
	}

	Vec3d interp(double pct, Vec3d start, Vec3d end) {
		return new Vec3d(
				MathHelper.lerp(pct, start.x, end.x),
				MathHelper.lerp(pct, start.y, end.y),
				MathHelper.lerp(pct, start.z, end.z)
		);
	}

	Vector2d getAngle(Vec3d vec) {
		double d0 = vec.x;
		double d1 = vec.y;
		double d2 = vec.z;
		double d3 = Math.sqrt(d0 * d0 + d2 * d2);
		double xr = (MathHelper.wrapDegrees((float)(-(MathHelper.atan2(d1, d3) * (double)(180F / (float)Math.PI)))));
		double yr = (MathHelper.wrapDegrees((float)(MathHelper.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F));
		return new Vector2d(xr, yr);
	}
}
