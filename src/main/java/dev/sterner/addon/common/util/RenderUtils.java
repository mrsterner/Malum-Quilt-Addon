package dev.sterner.addon.common.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import dev.sterner.addon.Addon;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.render.*;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.World;
import org.joml.Matrix4f;
import org.joml.Vector2d;
import org.joml.Vector4f;

import java.util.List;

public class RenderUtils {
	public static final Identifier CHECKMARK = Addon.id("textures/gui/check.png");

	public static void drawIcon(MatrixStack matrixStack, Identifier icon) {
		matrixStack.push();
		Matrix4f matrix = matrixStack.peek().getModel();
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferBuilder = tessellator.getBufferBuilder();
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.disableDepthTest();
		matrixStack.translate(12,12,0);
		RenderSystem.setShaderTexture(0, icon);
		bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
		bufferBuilder.vertex(matrix, -2, 6, 0).uv(0, 1).next();
		bufferBuilder.vertex(matrix, 6, 6, 0).uv(1, 1).next();
		bufferBuilder.vertex(matrix, 6, -2, 0).uv(1, 0).next();
		bufferBuilder.vertex(matrix, -2, -2, 0).uv(0, 0).next();
		tessellator.draw();
		matrixStack.pop();
	}



	public static void renderBeam(World world, float tickDelta, MatrixStack matrices, VertexConsumer consumer, Vec3d start, Vec3d end, int light, int overlay, int color){
		final int red = color >> 16;
		final int green = color >> 8;
		final int blue = color;
		float time = tickDelta + world.getTime();

		time /= 30;

		int steps = (int)end.subtract(start).length() * 10;

		if (steps > 40) steps = 40;
		Vector2d angle = getAngle(end.subtract(start));
		Matrix4f matrix4f = matrices.peek().getModel();
		Vec3d lastInterp = start;
		Vec3d lastUp = new Vec3d(0, 1, 0);
		Matrix4f cameraMatrix = new Matrix4f();
		cameraMatrix.rotate(MinecraftClient.getInstance().gameRenderer.getCamera().getRotation());

		for (int i = 0; i < steps; i++) {
			double delta = i / (double) steps;
			double amp = Math.min(delta, 1 - delta);
			amp *= 1.1;
			Vec3d interp = interp(delta, start, end);
			float dScl = 25;

			//TODO: correct texture offsets
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
				consumer.vertex(matrix4f, (float)(lastInterp.x + lastUp.x), (float)(lastInterp.y + lastUp.y), (float)(lastInterp.z + lastUp.z)).color(red, green, blue, 255).uv(1, 0).overlay(overlay).light(light).normal(0,1,0).next();
				consumer.vertex(matrix4f, (float)(interp.x + up.x),         (float)(interp.y + up.y),         (float)(interp.z + up.z))        .color(red, green, blue, 255).uv(1, 1).overlay(overlay).light(light).normal(0,1,0).next();
				consumer.vertex(matrix4f, (float)(interp.x - up.x),         (float)(interp.y - up.y),         (float)(interp.z - up.z))        .color(red, green, blue, 255).uv(0, 1).overlay(overlay).light(light).normal(0,1,0).next();
				consumer.vertex(matrix4f, (float)(lastInterp.x - lastUp.x), (float)(lastInterp.y - lastUp.y), (float)(lastInterp.z - lastUp.z)).color(red, green, blue, 255).uv(0, 0).overlay(overlay).light(light).normal(0,1,0).next();
			}

			lastInterp = interp;
			lastUp = up;
		}
	}

	public static Vec3d rotate(Vec3d src, Vector2d rotation) {
		return src.rotateX((float)rotation.x).rotateY((float)rotation.y);
	}

	public static Vec3d interp(double pct, Vec3d start, Vec3d end) {
		return new Vec3d(
				MathHelper.lerp(pct, start.x, end.x),
				MathHelper.lerp(pct, start.y, end.y),
				MathHelper.lerp(pct, start.z, end.z)
		);
	}

	public static Vector2d getAngle(Vec3d vec) {
		double d0 = vec.x;
		double d1 = vec.y;
		double d2 = vec.z;
		double d3 = Math.sqrt(d0 * d0 + d2 * d2);
		double xr = (MathHelper.wrapDegrees((float)(-(MathHelper.atan2(d1, d3) * (double)(180F / (float)Math.PI)))));
		double yr = (MathHelper.wrapDegrees((float)(MathHelper.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F));
		return new Vector2d(xr, yr);
	}
}
