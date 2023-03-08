package dev.sterner.addon.client.models.blockentity;

import dev.sterner.addon.Addon;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelLayer;

public class SpiritCrystalBlockEntityModel {
	public static final EntityModelLayer LAYER = new EntityModelLayer(Addon.id("crystal_ground"), "main");

	public final ModelPart crystal_0;
	public final ModelPart crystal_1;
	public final ModelPart crystal_2;
	public final ModelPart crystal_3;
	public final ModelPart crystal_4;
	public final ModelPart base;

	public SpiritCrystalBlockEntityModel(ModelPart root) {
		this.crystal_0 = root.getChild("crystal_0");
		this.crystal_1 = root.getChild("crystal_1");
		this.crystal_2 = root.getChild("crystal_2");
		this.crystal_3 = root.getChild("crystal_3");
		this.crystal_4 = root.getChild("crystal_4");
		this.base = root.getChild("base");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData crystal_0 = modelPartData.addChild("crystal_0", ModelPartBuilder.create().uv(7, 4).cuboid(-2.5F, -3.0F, -4.5F, 3.0F, 3.0F, 3.0F, new Dilation(0.02F))
				.uv(12, 12).cuboid(-3.5F, -2.0F, -7.5F, 3.0F, 2.0F, 3.0F, new Dilation(0.02F))
				.uv(1, 30).cuboid(2.5F, -1.0F, -3.5F, 2.0F, 1.0F, 2.0F, new Dilation(0.02F))
				.uv(2, 24).cuboid(0.5F, -1.0F, -8.5F, 3.0F, 1.0F, 3.0F, new Dilation(0.02F))
				.uv(1, 0).cuboid(-1.5F, -2.0F, -6.5F, 3.0F, 2.0F, 3.0F, new Dilation(0.03F)), ModelTransform.pivot(0.0F, 24.0F, 5.0F));

		ModelPartData crystal_1 = modelPartData.addChild("crystal_1", ModelPartBuilder.create().uv(16, 0).cuboid(-3.0F, -5.0F, -5.0F, 4.0F, 5.0F, 4.0F, new Dilation(0.0F))
				.uv(12, 12).cuboid(-4.0F, -3.0F, -8.0F, 4.0F, 3.0F, 4.0F, new Dilation(0.0F))
				.uv(0, 29).cuboid(2.0F, -2.0F, -4.0F, 3.0F, 2.0F, 3.0F, new Dilation(0.0F))
				.uv(0, 20).cuboid(0.0F, -4.0F, -9.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F))
				.uv(0, 0).cuboid(-2.0F, -6.0F, -7.0F, 4.0F, 6.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 5.0F));

		ModelPartData crystal_2 = modelPartData.addChild("crystal_2", ModelPartBuilder.create().uv(16, 0).cuboid(-3.0F, -6.0F, -5.0F, 4.0F, 6.0F, 4.0F, new Dilation(0.0F))
				.uv(12, 12).cuboid(-4.0F, -4.0F, -8.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F))
				.uv(0, 29).cuboid(2.0F, -2.0F, -4.0F, 3.0F, 2.0F, 3.0F, new Dilation(0.0F))
				.uv(0, 20).cuboid(0.0F, -5.0F, -9.0F, 4.0F, 5.0F, 4.0F, new Dilation(0.0F))
				.uv(0, 0).cuboid(-2.0F, -8.0F, -7.0F, 4.0F, 8.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 5.0F));

		ModelPartData crystal_3 = modelPartData.addChild("crystal_3", ModelPartBuilder.create().uv(16, 0).cuboid(-3.0F, -6.0F, -5.0F, 4.0F, 6.0F, 4.0F, new Dilation(0.0F))
				.uv(12, 12).cuboid(-4.0F, -6.0F, -8.0F, 4.0F, 6.0F, 4.0F, new Dilation(0.0F))
				.uv(0, 29).cuboid(2.0F, -3.0F, -4.0F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F))
				.uv(0, 20).cuboid(0.0F, -4.0F, -9.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F))
				.uv(0, 0).cuboid(-2.0F, -10.0F, -7.0F, 4.0F, 10.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 5.0F));

		ModelPartData crystal_4 = modelPartData.addChild("crystal_4", ModelPartBuilder.create().uv(16, 0).cuboid(-3.0F, -6.0F, -5.0F, 4.0F, 6.0F, 4.0F, new Dilation(0.0F))
				.uv(12, 12).cuboid(-4.0F, -8.0F, -8.0F, 4.0F, 8.0F, 4.0F, new Dilation(0.0F))
				.uv(0, 29).cuboid(2.0F, -3.0F, -4.0F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F))
				.uv(0, 20).cuboid(0.0F, -5.0F, -9.0F, 4.0F, 5.0F, 4.0F, new Dilation(0.0F))
				.uv(0, 0).cuboid(-2.0F, -12.0F, -7.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 5.0F));

		ModelPartData base = modelPartData.addChild("base", ModelPartBuilder.create().uv(29, 28).cuboid(2.0F, -1.0F, -4.0F, 3.0F, 1.0F, 3.0F, new Dilation(0.01F))
				.uv(24, 10).cuboid(-2.0F, -2.0F, -7.0F, 4.0F, 2.0F, 4.0F, new Dilation(0.01F))
				.uv(16, 24).cuboid(-3.0F, -3.0F, -5.0F, 4.0F, 3.0F, 4.0F, new Dilation(0.01F))
				.uv(28, 16).cuboid(-4.0F, -2.0F, -8.0F, 4.0F, 2.0F, 4.0F, new Dilation(0.01F))
				.uv(28, 22).cuboid(0.0F, -1.0F, -9.0F, 4.0F, 1.0F, 4.0F, new Dilation(0.01F)), ModelTransform.pivot(0.0F, 24.0F, 5.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}
}
