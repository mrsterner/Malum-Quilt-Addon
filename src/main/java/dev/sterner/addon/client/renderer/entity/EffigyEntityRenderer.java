package dev.sterner.addon.client.renderer.entity;

import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.sterner.addon.Addon;
import dev.sterner.addon.client.models.entity.EffigyEntityModel;
import dev.sterner.addon.common.entity.EffigyEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class EffigyEntityRenderer extends EntityRenderer<EffigyEntity> {
	private final EffigyEntityModel model;
	public EffigyEntityRenderer(EntityRendererFactory.Context context) {
		super(context);
		model = new EffigyEntityModel(context.getPart(EffigyEntityModel.LAYER));
	}

	@Override
	public void render(EffigyEntity entity, float yaw, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumers, int light) {
		matrixStack.push();
		matrixStack.scale(-1.0F, -1.0F, 1.0F);
		matrixStack.translate(0.0F, -1.501F, 0.0F);
		MinecraftClient minecraftClient = MinecraftClient.getInstance();
		boolean bl = this.isVisible(entity);
		boolean bl2 = !bl && !entity.isInvisibleTo(minecraftClient.player);
		boolean bl3 = minecraftClient.hasOutline(entity);
		RenderLayer renderLayer = this.getRenderLayer(entity, bl, bl2, bl3);
		if (renderLayer != null) {
			VertexConsumer vertexConsumer = vertexConsumers.getBuffer(renderLayer);
			int p = getOverlay(entity, 0);
			this.model.render(matrixStack, vertexConsumer, light, p, 1.0F, 1.0F, 1.0F, bl2 ? 0.15F : 1.0F);
		}

		matrixStack.pop();
		super.render(entity, yaw, tickDelta, matrixStack, vertexConsumers, light);

	}

	protected boolean isVisible(EffigyEntity entity) {
		return !entity.isInvisible();
	}

	@Nullable
	protected RenderLayer getRenderLayer(Entity entity, boolean showBody, boolean translucent, boolean showOutline) {
		Identifier identifier = getTexture((EffigyEntity) entity);
		if (translucent) {
			return RenderLayer.getItemEntityTranslucentCull(identifier);
		} else if (showBody) {
			return this.model.getLayer(identifier);
		} else {
			return showOutline ? RenderLayer.getOutline(identifier) : null;
		}
	}

	public static int getOverlay(LivingEntity entity, float whiteOverlayProgress) {
		return OverlayTexture.packUv(OverlayTexture.getU(whiteOverlayProgress), OverlayTexture.getV(entity.hurtTime > 0 || entity.deathTime > 0));
	}

	@Override
	public Identifier getTexture(EffigyEntity entity) {
		return Addon.id("textures/entity/effigy.png");
	}
}
