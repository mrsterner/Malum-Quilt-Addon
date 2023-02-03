package dev.sterner.addon.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferRenderer;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.sammy.lodestone.setup.LodestoneParticles;
import com.sammy.lodestone.setup.LodestoneShaders;
import com.sammy.lodestone.systems.rendering.ExtendedShader;
import com.sammy.lodestone.systems.rendering.particle.ParticleBuilders;
import dev.sterner.addon.AddonClient;
import dev.sterner.addon.common.BeamBlockEntity;
import dev.sterner.malum.common.blockentity.EtherBlockEntity;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.random.Xoroshiro128PlusPlusRandom;
import net.minecraft.world.World;
import org.joml.Matrix4f;
import org.joml.Vector2d;

import java.util.ArrayList;
import java.util.List;

public class BeamBlockEntityRenderer implements BlockEntityRenderer<BeamBlockEntity> {
	public BeamBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
	}

	@Override
	public void render(BeamBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		if(entity.getWorld() == null){
			return;
		}
		float f = (float)entity.getWorld().getTime() + tickDelta;
		final int red = Integer.parseInt(Integer.toString(0x7ecdfb, 16), 16) >> 16;
		final int green = Integer.parseInt(Integer.toString(0x7ecdfb, 16), 16) >> 8;
		final int blue = Integer.parseInt(Integer.toString(0x7ecdfb, 16), 16);

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
		Vec3d end = new Vec3d(10,0,0);
		matrices.push();
		int steps = (int)end.subtract(start).length() * 10;
		if (steps > 40) steps = 40;
		Vector2d angle = getAngle(end.subtract(start));
		Matrix4f matrix4f = matrices.peek().getModel();
		Vec3d lastInterp = start;
		for (int i = 1; i < steps; i++) {
			double delta = i / (double) steps;
			double amp = Math.min(delta, 1 - delta);
			Vec3d interp = interp(delta, start, end);

			Vec3d offset = new Vec3d(MathHelper.cos((float)delta + tickDelta + world.getTime()) * amp, MathHelper.sin((float)delta + tickDelta + world.getTime()) * amp, 0);
			interp = interp.add(rotate(offset, angle));
			builder.vertex(matrix4f, (float)lastInterp.x, (float)lastInterp.y + 1, (float)lastInterp.z).color(red, green, blue, 255).uv(0, 1).overlay(overlay).light(light).normal(0,1,0).next();
			builder.vertex(matrix4f, (float)lastInterp.x, (float)interp.y + 1, (float)interp.z).color(red, green, blue, 255).uv(1, 1).overlay(overlay).light(light).normal(0,1,0).next();
			builder.vertex(matrix4f, (float)lastInterp.x, (float)interp.y - 1, (float)interp.z).color(red, green, blue, 255).uv(1, 0).overlay(overlay).light(light).normal(0,1,0).next();
			builder.vertex(matrix4f, (float)lastInterp.x, (float)lastInterp.y - 1, (float)lastInterp.z).color(red, green, blue, 255).uv(0, 0).overlay(overlay).light(light).normal(0,1,0).next();
			lastInterp = interp;
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
