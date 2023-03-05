package dev.sterner.addon.client.renderer.entity;

import dev.sterner.addon.client.models.entity.VoidEntityModel;
import dev.sterner.addon.common.entity.VoidHand;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class VoidEntityRenderer extends GeoEntityRenderer<VoidHand> {
	public VoidEntityRenderer(EntityRendererFactory.Context renderManager) {
		super(renderManager, new VoidEntityModel());
	}
}
