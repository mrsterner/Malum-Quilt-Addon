package dev.sterner.addon.client.models.equipment;

import dev.sterner.addon.Addon;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.entity.LivingEntity;

public class HallowedGogglesModel<T extends LivingEntity> extends BipedEntityModel<T> {
	public static final EntityModelLayer LAYER = new EntityModelLayer(Addon.id("hallowed_goggles"), "main");
	private final ModelPart goggles;

	public HallowedGogglesModel(ModelPart root) {
		super(root, RenderLayer::getEntityTranslucent);
		this.goggles = root.getChild("head").getChild("goggles");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData data = BipedEntityModel.getModelData(Dilation.NONE, 0);
		ModelPartData root = data.getRoot();
		ModelPartData head = root.getChild(EntityModelPartNames.HEAD);
		ModelPartData goggles = head.addChild("goggles", ModelPartBuilder.create().uv(30, 48).cuboid(-4.5F, -30.0F, -3.5F, 9.0F, 2.0F, 8.0F, new Dilation(-0.001F)), ModelTransform.pivot(0.0F, 24.5F, 0.0F));

		ModelPartData eyes = goggles.addChild("eyes", ModelPartBuilder.create().uv(30, 58).cuboid(0.5F, -30.5F, -5.0F, 4.0F, 4.0F, 2.0F, new Dilation(0.0F))
				.uv(30, 48).cuboid(-0.5F, -29.5F, -4.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
				.uv(30, 58).cuboid(-4.5F, -30.5F, -5.0F, 4.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		return TexturedModelData.of(data, 64, 64);
	}

}
