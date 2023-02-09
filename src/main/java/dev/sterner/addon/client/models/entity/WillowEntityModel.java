package dev.sterner.addon.client.models.entity;

import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.sterner.addon.Addon;
import dev.sterner.addon.common.entity.WillowEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;

public class WillowEntityModel<T extends WillowEntity> extends EntityModel<T> {
	public static final EntityModelLayer LAYER = new EntityModelLayer(Addon.id("willow"), "main");
	private final ModelPart main;

	public WillowEntityModel(ModelPart root) {
		this.main = root.getChild("main");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData main = modelPartData.addChild("main", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData body = main.addChild("body", ModelPartBuilder.create().uv(0, 25).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 5.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -11.0F, -0.2F, 0.2182F, 0.0F, 0.0F));
		ModelPartData body2 = body.addChild("body2", ModelPartBuilder.create().uv(14, 13).cuboid(-2.5F, 0.0F, -2.5F, 5.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 3.5F, 0.0F, 0.1745F, 0.0F, 0.0F));
		ModelPartData body3 = body2.addChild("body3", ModelPartBuilder.create().uv(0, 0).cuboid(-4.5F, 0.0F, -1.5F, 6.0F, 2.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(1.5F, 3.0F, -1.5F, 0.1745F, 0.0F, 0.0F));
		ModelPartData feet = body3.addChild("feet", ModelPartBuilder.create().uv(32, 15).cuboid(-1.5F, -1.0F, 0.5F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)).uv(30, 23).cuboid(-4.5F, -1.0F, 0.5F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.5F, 2.0F, -0.5F));
		ModelPartData head = main.addChild("head", ModelPartBuilder.create().uv(18, 3).cuboid(-2.5F, -12.0F, -2.5F, 5.0F, 2.0F, 4.0F, new Dilation(0.0F)).uv(0, 38).cuboid(2.5F, -18.0F, -2.5F, 0.0F, 6.0F, 4.0F, new Dilation(0.0F)).uv(0, 29).cuboid(-2.5F, -18.0F, -2.5F, 0.0F, 6.0F, 4.0F, new Dilation(0.0F)).uv(0, 7).cuboid(-2.5F, -18.0F, -2.5F, 5.0F, 0.0F, 4.0F, new Dilation(0.0F)).uv(18, 42).cuboid(-2.5F, -18.0F, 1.5F, 5.0F, 6.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		ModelPartData head2 = head.addChild("head2", ModelPartBuilder.create().uv(0, 17).cuboid(-3.5F, -4.0F, -1.3F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(1.5F, -12.0F, -1.5F));
		ModelPartData head3 = head2.addChild("head3", ModelPartBuilder.create().uv(17, 0).cuboid(-2.0F, -3.0F, 0.0F, 4.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-1.5F, -4.0F, -1.2F, -0.9163F, 0.0F, 0.0F));
		ModelPartData hood = head.addChild("hood", ModelPartBuilder.create().uv(16, 21).cuboid(-2.0F, 0.0F, 0.0F, 4.0F, 6.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -17.5F, 1.5F, -0.2182F, 0.0F, 0.0F));
		ModelPartData hood2 = hood.addChild("hood2", ModelPartBuilder.create().uv(27, 27).cuboid(-1.5F, -1.0F, 0.5F, 3.0F, 4.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 1.5F, 1.9F, -0.3927F, 0.0F, 0.0F));
		ModelPartData hood3 = hood2.addChild("hood3", ModelPartBuilder.create().uv(32, 0).cuboid(-1.0F, 0.0F, 0.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -0.5F, 3.5F, -0.6109F, 0.0F, 0.0F));
		ModelPartData leftArm = main.addChild("leftArm", ModelPartBuilder.create().uv(14, 30).cuboid(0.25F, 0.2F, -1.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(1.75F, -10.3F, -0.5F, 0.2618F, 0.0F, -0.3927F));
		ModelPartData cube_r1 = leftArm.addChild("cube_r1", ModelPartBuilder.create().uv(30, 19).cuboid(-2.0F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F, new Dilation(0.1F)), ModelTransform.of(1.25F, -0.2F, 0.0F, 0.829F, 0.0F, 0.0F));
		ModelPartData leftHand = leftArm.addChild("leftHand", ModelPartBuilder.create().uv(3, 19).cuboid(-0.5F, 0.0F, -1.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(1.25F, 4.2F, 0.0F));
		ModelPartData rightArm = main.addChild("rightArm", ModelPartBuilder.create().uv(14, 30).mirrored().cuboid(-2.25F, 0.2F, -1.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-1.75F, -10.3F, -0.5F, 0.2618F, 0.0F, 0.3927F));
		ModelPartData cube_r2 = rightArm.addChild("cube_r2", ModelPartBuilder.create().uv(30, 19).mirrored().cuboid(-1.0F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F, new Dilation(0.1F)).mirrored(false), ModelTransform.of(-1.25F, -0.2F, 0.0F, 0.829F, 0.0F, 0.0F));
		ModelPartData rightHand = rightArm.addChild("rightHand", ModelPartBuilder.create().uv(3, 19).mirrored().cuboid(-0.5F, 0.0F, -1.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-1.25F, 4.2F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}


	@Override
	public void setAngles(WillowEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		main.render(matrices, vertices, light, overlay, red, green, blue, alpha);
	}
}
