package dev.sterner.addon.client.renderer.equipment.armor;

import dev.sterner.addon.Addon;
import dev.sterner.addon.client.models.equipment.HallowedGogglesModel;
import dev.sterner.malum.Malum;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class HallowedGogglesRenderer implements ArmorRenderer {
	private static HallowedGogglesModel<LivingEntity> model;
	public HallowedGogglesRenderer() {
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, BipedEntityModel<LivingEntity> contextModel) {
		if (model == null) {
			model = new HallowedGogglesModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(HallowedGogglesModel.LAYER));
		}
		if (slot == EquipmentSlot.HEAD) {
			contextModel.setAttributes(model);
			model.setVisible(true);
			ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, model, Addon.id("textures/entity/armor/hallowed_goggles.png"));
		}
	}
}
