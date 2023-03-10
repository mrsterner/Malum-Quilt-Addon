package dev.sterner.addon.client.renderer;

import dev.sterner.addon.Addon;
import dev.sterner.addon.client.registry.AddonRenderLayers;
import dev.sterner.addon.common.blockentity.BeamBlockEntity;
import dev.sterner.addon.common.util.MathUtils;
import dev.sterner.addon.common.util.RenderUtils;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class BeamBlockEntityRenderer implements BlockEntityRenderer<BeamBlockEntity> {
	public BeamBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
	}

	@Override
	public void render(BeamBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		if(entity.getWorld() == null){
			return;
		}
		Vec3d start = Vec3d.ZERO;
		Vec3d end = new Vec3d(0,2,8);
		float f = (float)entity.getWorld().getTime() + tickDelta;
		int color = Integer.parseInt(Integer.toString(0x7ecdfb, 16), 16);
		Identifier texture = Addon.id("textures/misc/beam.png");
		float wave = MathUtils.zeroToOne(f, 0.01f);
		matrices.push();
		//matrices.multiply(Axis.Y_POSITIVE.rotationDegrees(360 * wave));
		RenderUtils.renderBeam(entity.getWorld(), tickDelta, matrices, vertexConsumers.getBuffer(AddonRenderLayers.getEnergySwirl(texture,0, MathUtils.zeroToOne(f, 0.1f))), start, end, light, overlay, color);
		matrices.pop();
	}

	@Override
	public boolean rendersOutsideBoundingBox(BeamBlockEntity blockEntity) {
		return true;
	}
}
