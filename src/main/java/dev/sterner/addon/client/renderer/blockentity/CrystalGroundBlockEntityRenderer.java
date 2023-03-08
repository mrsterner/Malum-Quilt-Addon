package dev.sterner.addon.client.renderer.blockentity;

import dev.sterner.addon.Addon;
import dev.sterner.addon.client.models.blockentity.CrystalGroundBlockEntityModel;
import dev.sterner.addon.client.registry.AddonRenderLayers;
import dev.sterner.addon.common.blockentity.CrystalGroundBlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Axis;
import net.minecraft.util.math.MathHelper;

public class CrystalGroundBlockEntityRenderer implements BlockEntityRenderer<CrystalGroundBlockEntity> {
	private final CrystalGroundBlockEntityModel model;
	public CrystalGroundBlockEntityRenderer(BlockEntityRendererFactory.Context ctx){
		model = new CrystalGroundBlockEntityModel(ctx.getLayerModelPart(CrystalGroundBlockEntityModel.LAYER));
	}

	@Override
	public void render(CrystalGroundBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		float r = -1f;
		float g = -1f;
		float b = -1f;

		if(entity.type != null){
			float colorMultiplier = MathHelper.clamp((1 + (float)entity.power) / (entity.MAX_POWER), 0 , 1);// goes from 0 -> 1
			r = colorMultiplier * (1 - entity.type.getColor().getRed() / 255f);
			g = colorMultiplier * (1 - entity.type.getColor().getGreen() / 255f);
			b = colorMultiplier * (1 - entity.type.getColor().getBlue() / 255f);
		}

		matrices.push();
		matrices.translate(0.5,1.5,0.5);
		matrices.multiply(Axis.X_POSITIVE.rotationDegrees(180));
		model.crystal_4.render(matrices, vertexConsumers.getBuffer(AddonRenderLayers.getGlowy(Addon.id("textures/block/crystal_ground.png"))), light, overlay, 1 - r ,1 - g,1 - b,1);
		model.base.render(matrices, vertexConsumers.getBuffer(AddonRenderLayers.getGlowy(Addon.id("textures/block/crystal_ground.png"))), light, overlay, 1 ,1,1,1);
		matrices.pop();
	}
}
