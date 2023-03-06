package dev.sterner.addon.client.models.blockentity;

import dev.sterner.addon.Addon;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelLayer;

public class CrystalBlockEntityModel {
	public static final EntityModelLayer LAYER = new EntityModelLayer(Addon.id("crystal"), "main");

	public final ModelPart main;
	public CrystalBlockEntityModel(ModelPart root) {
		this.main = root.getChild("main");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild("main", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -11.0F, -1.0F, 3.0F, 7.0F, 3.0F, new Dilation(0.0F))
				.uv(0, 16).cuboid(-2.0F, -11.0F, -1.0F, 3.0F, 7.0F, 3.0F, new Dilation(-0.5F))
				.uv(12, 0).cuboid(-0.5F, -10.0F, -1.5F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F))
				.uv(8, 10).cuboid(-2.5F, -7.0F, 0.5F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F))
				.uv(0, 10).cuboid(-1.0F, -12.0F, 0.5F, 2.0F, 4.0F, 2.0F, new Dilation(-0.01F)), ModelTransform.pivot(0.5F, 24.0F, -0.5F));
		return TexturedModelData.of(modelData, 32, 32);
	}
}
