package dev.sterner.addon.client.renderer.blockentity;

import dev.sterner.addon.Addon;
import dev.sterner.addon.client.models.blockentity.CrystalBlockEntityModel;
import dev.sterner.addon.client.registry.AddonRenderLayers;
import dev.sterner.addon.common.blockentity.CrystalBlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class CrystalBlockEntityRenderer implements BlockEntityRenderer<CrystalBlockEntity> {
	private final CrystalBlockEntityModel model;
	public CrystalBlockEntityRenderer(BlockEntityRendererFactory.Context ctx){
		model = new CrystalBlockEntityModel(ctx.getLayerModelPart(CrystalBlockEntityModel.LAYER));
	}

	@Override
	public void render(CrystalBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
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
		matrices.translate(0.5,-0.5,0.5);
		model.main.render(matrices, vertexConsumers.getBuffer(AddonRenderLayers.getGlowy(Addon.id("textures/block/crystal_damned.png"))), light, overlay, 1 - r ,1 - g,1 - b,1);
		matrices.pop();
	}
}
