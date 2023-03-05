package dev.sterner.addon.client.renderer.entity;

import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.sterner.addon.client.models.entity.VoidEntityModel;
import dev.sterner.addon.common.entity.VoidHandEntity;
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
	public void preRender(MatrixStack poseStack, VoidHandEntity animatable, BakedGeoModel model, VertexConsumerProvider bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		poseStack.scale(2,2,2);
		super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
	}
}
