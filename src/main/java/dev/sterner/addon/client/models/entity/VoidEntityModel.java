package dev.sterner.addon.client.models.entity;

import dev.sterner.addon.Addon;
import dev.sterner.addon.common.entity.VoidHand;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class VoidEntityModel extends DefaultedEntityGeoModel<VoidHand> {
	public VoidEntityModel() {
		super(Addon.id("void"));
	}
}
