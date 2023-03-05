package dev.sterner.addon.client.models.entity;

import dev.sterner.addon.Addon;
import dev.sterner.addon.common.entity.VoidHandEntity;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class VoidEntityModel extends DefaultedEntityGeoModel<VoidHandEntity> {
	public VoidEntityModel() {
		super(Addon.id("void"));
	}
}
