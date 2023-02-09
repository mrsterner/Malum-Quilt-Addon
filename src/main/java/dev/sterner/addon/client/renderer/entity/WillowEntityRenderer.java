package dev.sterner.addon.client.renderer.entity;

import dev.sterner.addon.Addon;
import dev.sterner.addon.client.models.entity.WillowEntityModel;
import dev.sterner.addon.common.entity.WillowEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Identifier;

public class WillowEntityRenderer extends MobEntityRenderer<WillowEntity, WillowEntityModel<WillowEntity>> {

	public WillowEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new WillowEntityModel<>(context.getPart(WillowEntityModel.LAYER)), 0.2f);
	}

	@Override
	public void render(WillowEntity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
		super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
		if(mobEntity.world.getTime() % 10 == 0){
			double x = mobEntity.getX();
			double y = mobEntity.getY();
			double z = mobEntity.getZ();
			mobEntity.getWorld().addParticle(ParticleTypes.SOUL_FIRE_FLAME,
					x,
					y + 1,
					z,
					0, 0, 0);
		}

	}

	@Override
	public Identifier getTexture(WillowEntity entity) {
		return Addon.id("textures/entity/willow.png");
	}
}
