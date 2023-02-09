package dev.sterner.addon.client.renderer.entity;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.sammy.lodestone.setup.LodestoneParticleRegistry;
import com.sammy.lodestone.systems.particle.WorldParticleBuilder;
import com.sammy.lodestone.systems.particle.data.ColorParticleData;
import com.sammy.lodestone.systems.particle.data.GenericParticleData;
import com.sammy.lodestone.systems.particle.data.SpinParticleData;
import dev.sterner.addon.client.models.entity.WillowEntityModel;
import dev.sterner.addon.common.entity.WillowEntity;
import dev.sterner.addon.common.registry.AddonSpiritTypes;
import dev.sterner.malum.common.registry.MalumSpiritTypeRegistry;
import dev.sterner.malum.common.spirit.MalumSpiritType;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.DynamicGeoEntityRenderer;

import java.awt.*;

public class WillowEntityRenderer extends DynamicGeoEntityRenderer<WillowEntity> {
	public MalumSpiritType type;

	public WillowEntityRenderer(EntityRendererFactory.Context renderManager) {
		super(renderManager, new WillowEntityModel());
		type = AddonSpiritTypes.DAMNED_SPIRIT;
	}

	@Override
	public void postRender(MatrixStack poseStack, WillowEntity animatable, BakedGeoModel model, VertexConsumerProvider bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		super.postRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
		if (model.getBone("particle").isPresent()) {
			Color color = type.getColor();
			double x = -model.getBone("particle").get().getWorldPosition().x;
			double y = model.getBone("particle").get().getWorldPosition().y;
			double z = -model.getBone("particle").get().getWorldPosition().z;
			WorldParticleBuilder.create(LodestoneParticleRegistry.SMOKE_PARTICLE)
					.setTransparencyData(GenericParticleData.create(0.15f, 0f).build())
					.setScaleData(GenericParticleData.create(0.15f, 0).build())
					.setSpinData(SpinParticleData.create(0.2f).build())
					.setColorData(ColorParticleData.create(color, color.darker()).build())
					.setLifetime(20)
					.setRandomMotion(0.02f)
					.setRandomOffset(0.1f, 0.1f)
					.enableNoClip()
					.repeat(animatable.world, x, y + 0.2, z, 4);
		}
	}
}
