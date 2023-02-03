package dev.sterner.addon.client;

import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.sterner.addon.AddonClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Axis;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;

import java.util.List;
import java.util.function.Consumer;

public class RenderHelper {
	/**
	 * Draw a segmented line between two points, subdividing the line into a number of segments
	 * @param buffer The buffer to draw to
	 * @param ps The pose stack to draw with
	 * @param lineWidth The width of the line
	 * @param points The points to draw between
	 **/

	public static void drawSteppedLineBetween(VertexConsumerProvider buffer, MatrixStack ps, List<Vec3d> points, float lineWidth, int r, int g, int b, int a, float v, int light, int overlay) {
		Vec3d origin = points.get(0);
		for (int i = 1; i < points.size(); i++) {
			Vec3d target = points.get(i);
			drawLineBetween(buffer, ps, origin, target, lineWidth, r, g, b, a, v, light, overlay);
			origin = target;
		}
	}

	/**
	 * Draw a segmented line between two points, subdividing the line into a number of segments
	 * @param buffer The buffer to draw to
	 * @param ps The pose stack to draw with
	 * @param start The start point
	 * @param end The end point
	 * @param steps The number of steps to divide the line into
	 * @param lineWidth The width of the line
	 * @param pointConsumer A consumer to call for each point in the line
	 */

	public static void drawSteppedLineBetween(VertexConsumerProvider buffer, MatrixStack ps, Vec3d start, Vec3d end, int steps, float lineWidth, int r, int g, int b, int a, Consumer<Vec3d> pointConsumer, float v, int light, int overlay) {
		Vec3d origin = start;
		for (int i = 1; i <= steps; i++) {
			Vec3d target = start.add(end.subtract(start).multiply(i / (float) steps));
			pointConsumer.accept(target);
			drawLineBetween(buffer, ps, origin, target, lineWidth, r, g, b, a, v, light, overlay);
			origin = target;
		}
	}

	/**
	 * Draw a line between two points
	 * @param buffer The buffer to draw to
	 * @param ps The pose stack to draw with
	 * @param local The start point
	 * @param target The end point
	 * @param lineWidth The width of the line
	 */

	public static void drawLineBetween(VertexConsumerProvider buffer, MatrixStack ps, Vec3d local, Vec3d target, float lineWidth, int r, int g, int b, int a, float speed, int light, int overlay) {
		//VertexConsumer builder = buffer.getBuffer(RenderLayer.getLeash());
		VertexConsumer builder = buffer.getBuffer(AddonClient.getEnergySwirl(0, speed));

		//Calculate yaw
		float rotY = (float) MathHelper.atan2(target.x - local.x, target.z - local.z);

		//Calculate pitch
		double distX = target.x - local.x;
		double distZ = target.z - local.z;
		float rotX = (float) MathHelper.atan2(target.y - local.y, MathHelper.sqrt((float) (distX * distX + distZ * distZ)));

		ps.push();

		//Translate to start point
		ps.translate(local.x, local.y, local.z);
		//Rotate to point towards end point
		ps.multiply(Axis.Y_POSITIVE.rotation(rotY));
		ps.multiply(Axis.X_NEGATIVE.rotation(rotX));

		//Calculate distance between points -> length of the line
		float distance = (float) local.distanceTo(target);

		Matrix4f matrix = ps.peek().getModel();
		float halfWidth = lineWidth / 2F;

		//Draw horizontal quad
		builder.vertex(matrix, -halfWidth, 0, 0).color(r, g, b, a).uv(0.0F, 1.0F).overlay(overlay).light(light).normal(0.0F, 1.0F, 0.0F).next();
		builder.vertex(matrix, halfWidth, 0, 0).color(r, g, b, a).uv(1.0F, 1.0F).overlay(overlay).light(light).normal(0.0F, 1.0F, 0.0F).next();
		builder.vertex(matrix, halfWidth, 0, distance).color(r, g, b, a).uv(1.0F, 0.0F).overlay(overlay).light(light).normal(0.0F, 1.0F, 0.0F).next();
		builder.vertex(matrix, -halfWidth, 0, distance).color(r, g, b, a).uv(0.0F, 0.0F).overlay(overlay).light(light).normal(0.0F, 1.0F, 0.0F).next();

		//Draw vertical Quad
		builder.vertex(matrix, 0, -halfWidth, 0).color(r, g, b, a).uv(0.0F, 1.0F).overlay(overlay).light(light).normal(0.0F, 1.0F, 0.0F).next();
		builder.vertex(matrix, 0, halfWidth, 0).color(r, g, b, a).uv(1.0F, 1.0F).overlay(overlay).light(light).normal(0.0F, 1.0F, 0.0F).next();
		builder.vertex(matrix, 0, halfWidth, distance).color(r, g, b, a).uv(1.0F, 0.0F).overlay(overlay).light(light).normal(0.0F, 1.0F, 0.0F).next();
		builder.vertex(matrix, 0, -halfWidth, distance).color(r, g, b, a).uv(0.0F, 0.0F).overlay(overlay).light(light).normal(0.0F, 1.0F, 0.0F).next();

		ps.pop();
	}
}
