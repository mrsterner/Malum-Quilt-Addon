package dev.sterner.addon.client.models.entity;

import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.sterner.addon.Addon;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;

public class EffigyEntityModel extends Model {
	public static final EntityModelLayer LAYER = new EntityModelLayer(Addon.id("effigy"), "main");
	private final ModelPart bone;

	public EffigyEntityModel(ModelPart root) {
		super(RenderLayer::getEntityTranslucent);
		this.bone = root.getChild("bone");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData data = BipedEntityModel.getModelData(Dilation.NONE, 0);
		ModelPartData root = data.getRoot();
		ModelPartData bone = root.addChild("bone", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData rightLeg = bone.addChild("rightLeg", ModelPartBuilder.create().uv(16, 32).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(1.9F, 12.0F, 0.0F, 0.0F, 0.0F, -0.0436F));
		ModelPartData leftLeg = bone.addChild("leftLeg", ModelPartBuilder.create().uv(32, 0).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-1.9F, 12.0F, 0.0F, 0.0F, 0.0F, 0.0436F));
		ModelPartData twig4 = leftLeg.addChild("twig4", ModelPartBuilder.create().uv(38, 28).cuboid(-0.5F, 0.0F, -1.0F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)).uv(44, 44).cuboid(-1.5F, -3.0F, 3.001F, 3.0F, 4.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(4.4F, 5.0F, 2.0F, 0.829F, -0.6109F, 0.5236F));
		ModelPartData twig5 = twig4.addChild("twig5", ModelPartBuilder.create().uv(0, 4).cuboid(-0.5F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)).uv(44, 40).cuboid(-1.5F, -1.0F, 3.001F, 3.0F, 4.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 1.0F, 3.0F, 0.6109F, -0.3054F, 0.2618F));
		ModelPartData twig6 = twig5.addChild("twig6", ModelPartBuilder.create().uv(12, 32).cuboid(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)).uv(38, 37).cuboid(-3.0F, -2.0F, 3.001F, 4.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-0.5F, 0.0F, 3.0F, 0.3054F, 0.4363F, 0.0F));
		ModelPartData twig18 = twig4.addChild("twig18", ModelPartBuilder.create().uv(0, 4).cuboid(-0.5F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)).uv(38, 42).cuboid(-1.5F, -1.0F, 3.001F, 3.0F, 4.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 1.0F, 3.0F, 0.7854F, 1.0472F, 0.0436F));
		ModelPartData leftArm = bone.addChild("leftArm", ModelPartBuilder.create().uv(0, 32).cuboid(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-5.0F, 2.0F, 0.0F, 0.0F, 0.0F, 0.1309F));
		ModelPartData rightArm = bone.addChild("rightArm", ModelPartBuilder.create().uv(24, 16).cuboid(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(5.0F, 2.0F, 0.0F, 0.0F, 0.0F, -0.1309F));
		ModelPartData twig7 = rightArm.addChild("twig7", ModelPartBuilder.create().uv(38, 28).cuboid(-0.5F, 0.0F, -3.0F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)).uv(40, 19).cuboid(-0.5F, -1.0F, -3.001F, 4.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(1.5F, 1.0F, -2.0F, -0.2182F, 0.2618F, 0.0F));
		ModelPartData twig16 = twig7.addChild("twig16", ModelPartBuilder.create().uv(0, 4).cuboid(0.0F, -1.0F, -3.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)).uv(44, 25).cuboid(-1.0F, -1.0F, -3.001F, 3.0F, 4.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-0.5F, 0.0F, -3.0F, -1.7453F, -0.5672F, 0.6981F));
		ModelPartData twig17 = twig16.addChild("twig17", ModelPartBuilder.create().uv(28, 32).cuboid(0.25F, -0.5F, -3.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-0.25F, -0.5F, -3.0F, 0.3927F, -0.48F, 0.0F));
		ModelPartData twig17_r1 = twig17.addChild("twig17_r1", ModelPartBuilder.create().uv(38, 37).cuboid(-5.0F, -1.5F, 0.0F, 4.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-0.75F, 0.0F, -3.001F, 0.0F, 0.0F, -3.1416F));
		ModelPartData twig8 = twig7.addChild("twig8", ModelPartBuilder.create().uv(0, 4).cuboid(0.0F, -1.0F, -3.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)).uv(44, 25).cuboid(-1.0F, -1.0F, -3.001F, 3.0F, 4.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-0.5F, 1.0F, -3.0F, -0.9599F, -0.3927F, 0.0F));
		ModelPartData twig9 = twig8.addChild("twig9", ModelPartBuilder.create().uv(12, 32).cuboid(0.25F, -0.5F, -3.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)).uv(38, 37).cuboid(-2.75F, -1.5F, -3.001F, 4.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-0.25F, -0.5F, -3.0F, -0.7418F, -0.3491F, 0.0F));
		ModelPartData body = bone.addChild("body", ModelPartBuilder.create().uv(0, 16).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		ModelPartData twig13 = body.addChild("twig13", ModelPartBuilder.create().uv(32, 37).cuboid(-0.5F, 0.0F, -3.0F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)).uv(44, 0).cuboid(-1.5F, -3.0F, -2.99F, 3.0F, 4.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-3.5F, 8.0F, -2.0F, -0.6109F, 0.7854F, 0.0F));
		ModelPartData twig14 = twig13.addChild("twig14", ModelPartBuilder.create().uv(12, 32).cuboid(-0.5F, -1.0F, -3.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)).uv(38, 42).cuboid(-1.5F, -1.0F, -2.99F, 3.0F, 4.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 1.0F, -3.0F, -0.4363F, -0.3491F, -0.2182F));
		ModelPartData twig14_r1 = twig14.addChild("twig14_r1", ModelPartBuilder.create().uv(12, 32).cuboid(-0.5F, -0.5F, -3.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)).uv(38, 42).cuboid(-1.5F, -0.5F, -3.001F, 3.0F, 4.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -0.5F, 0.0F, 0.829F, -0.1745F, 0.8727F));
		ModelPartData twig15 = twig14.addChild("twig15", ModelPartBuilder.create().uv(32, 37).cuboid(0.0F, -0.5F, -3.0F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)).uv(32, 42).cuboid(-1.0F, -3.5F, -3.001F, 3.0F, 4.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-0.5F, -0.5F, -3.0F, -0.3054F, 0.3054F, 0.0F));
		ModelPartData twig1 = body.addChild("twig1", ModelPartBuilder.create().uv(32, 32).cuboid(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)).uv(38, 33).cuboid(-0.5F, -1.5F, 3.001F, 4.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-2.5F, 3.5F, 2.0F, 0.3054F, -0.3927F, 0.0F));
		ModelPartData twig2 = twig1.addChild("twig2", ModelPartBuilder.create().uv(0, 4).cuboid(-1.0F, -0.5F, 0.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)).uv(30, 0).cuboid(-2.0F, -0.5F, 3.001F, 3.0F, 4.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.5F, 0.0F, 3.0F, 0.4363F, -0.6109F, 0.0F));
		ModelPartData twig2_r1 = twig2.addChild("twig2_r1", ModelPartBuilder.create().uv(0, 4).cuboid(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)).uv(44, 25).cuboid(-1.5F, -0.5F, 3.001F, 3.0F, 4.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-0.5F, 0.0F, 0.0F, 0.2618F, 1.1781F, -0.6109F));
		ModelPartData twig3 = twig2.addChild("twig3", ModelPartBuilder.create().uv(0, 0).cuboid(0.0F, -0.5F, 0.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)).uv(24, 4).cuboid(-3.0F, -1.5F, 3.001F, 4.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, 0.0F, 3.0F, 0.0F, 1.0036F, -0.48F));
		ModelPartData head = bone.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F)).uv(0, 48).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.2F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.0873F, -0.0436F, 0.0436F));
		ModelPartData twig10 = head.addChild("twig10", ModelPartBuilder.create().uv(46, 33).cuboid(-0.5F, -3.0F, 0.0F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F)).uv(20, 0).cuboid(-1.5F, -3.001F, -3.0F, 3.0F, 0.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-3.5F, -8.0F, -2.0F, -0.7418F, -0.6545F, -0.3054F));
		ModelPartData twig10_r1 = twig10.addChild("twig10_r1", ModelPartBuilder.create().uv(32, 46).cuboid(-0.25F, -3.75F, -0.5F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F)).uv(20, 0).cuboid(-1.25F, -3.751F, -3.5F, 3.0F, 0.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-0.25F, -0.25F, 0.5F, 1.0036F, 0.0873F, -1.1345F));
		ModelPartData twig11 = twig10.addChild("twig11", ModelPartBuilder.create().uv(0, 32).cuboid(-0.5F, -3.0F, -1.25F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)).uv(16, 16).cuboid(-1.5F, -3.001F, -1.25F, 3.0F, 0.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -3.0F, 1.25F, 0.5236F, -0.1309F, 0.7854F));
		ModelPartData twig12 = twig11.addChild("twig12", ModelPartBuilder.create().uv(0, 16).cuboid(-1.0F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)).uv(21, 4).cuboid(-4.0F, -3.001F, -1.5F, 4.0F, 0.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.5F, -3.0F, -0.75F, 0.4363F, 0.0873F, -0.9599F));
		return TexturedModelData.of(data, 64, 64);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		this.bone.render(matrices, vertices, light, overlay, red, green, blue, alpha);
	}
}
