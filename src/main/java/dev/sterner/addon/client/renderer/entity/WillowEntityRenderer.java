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
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.BatEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import java.awt.*;

public class WillowEntityRenderer extends GeoEntityRenderer<WillowEntity> {
	public MalumSpiritType type;
	private int currentTick = -1;

	public WillowEntityRenderer(EntityRendererFactory.Context renderManager) {
		super(renderManager, new WillowEntityModel());
		type = AddonSpiritTypes.DAMNED_SPIRIT;
	}

	@Override
	public void postRender(MatrixStack poseStack, WillowEntity animatable, BakedGeoModel model, VertexConsumerProvider bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		if (this.currentTick < 0 || this.currentTick != animatable.age) {
			this.currentTick = animatable.age;

			this.model.getBone("particle").ifPresent(ear -> {
				Color color = type.getColor();
				WorldParticleBuilder.create(LodestoneParticleRegistry.SMOKE_PARTICLE)
						.setTransparencyData(GenericParticleData.create(0.15f, 0f).build())
						.setScaleData(GenericParticleData.create(0.15f, 0).build())
						.setSpinData(SpinParticleData.create(0.2f).build())
						.setColorData(ColorParticleData.create(color, color.darker()).build())
						.setLifetime(20)
						.setRandomMotion(0.02f)
						.setRandomOffset(0.1f, 0.1f)
						.enableNoClip()
						.repeat(animatable.getCommandSenderWorld(), ear.getWorldPosition().x(), ear.getWorldPosition().y() + 1, ear.getWorldPosition().z(), 10);
			});
		}
		super.postRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
	}
}
