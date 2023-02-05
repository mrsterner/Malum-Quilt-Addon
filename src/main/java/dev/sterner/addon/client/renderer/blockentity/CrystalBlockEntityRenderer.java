package dev.sterner.addon.client.renderer.blockentity;

import dev.sterner.addon.Addon;
import dev.sterner.addon.client.models.blockentity.CrystalBlockEntityModel;
import dev.sterner.addon.client.registry.AddonRenderLayers;
import dev.sterner.addon.common.block.CrystalBlock;
import dev.sterner.addon.common.blockentity.CrystalBlockEntity;
import dev.sterner.malum.common.registry.MalumSpiritTypeRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.impl.client.indigo.renderer.helper.ColorHelper;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

import java.awt.*;

public class CrystalBlockEntityRenderer implements BlockEntityRenderer<CrystalBlockEntity> {
	private final CrystalBlockEntityModel model;
	public CrystalBlockEntityRenderer(BlockEntityRendererFactory.Context ctx){
		model = new CrystalBlockEntityModel(ctx.getLayerModelPart(CrystalBlockEntityModel.LAYER));
	}

	@Override
	public void render(CrystalBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

		int	r = entity.color.getRed();
		int g = entity.color.getGreen();
		int b = entity.color.getBlue();


		matrices.push();
		matrices.translate(0.5,-0.5,0.5);
		model.main.render(matrices, vertexConsumers.getBuffer(AddonRenderLayers.getGlowy(Addon.id("textures/block/crystal.png"))), light, overlay, 1 - r,1 - g, 1 - b,1);
		matrices.pop();
	}
}
