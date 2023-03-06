package dev.sterner.addon.client.renderer.entity;

import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.sterner.addon.client.models.entity.VoidEntityModel;
import dev.sterner.addon.common.entity.VoidHandEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class VoidEntityRenderer extends GeoEntityRenderer<VoidHandEntity> {
	public VoidEntityRenderer(EntityRendererFactory.Context renderManager) {
		super(renderManager, new VoidEntityModel());
	}

	@Override
	public void preRender(MatrixStack matrixStack, VoidHandEntity voidHandEntity, BakedGeoModel model, VertexConsumerProvider vertexConsumers, VertexConsumer vertexConsumer, boolean isReRender, float deltaTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		matrixStack.scale(2,2,2);
		super.preRender(matrixStack, voidHandEntity, model, vertexConsumers, vertexConsumer, isReRender, deltaTick, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public int getPackedOverlay(VoidHandEntity animatable, float u) {
		return OverlayTexture.DEFAULT_UV;
	}
}
