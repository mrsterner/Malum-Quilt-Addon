package dev.sterner.addon.client.models.entity;

import dev.sterner.addon.Addon;
import dev.sterner.addon.common.entity.WillowEntity;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class WillowEntityModel extends DefaultedEntityGeoModel<WillowEntity> {

	public WillowEntityModel() {
		super(Addon.id("willow"));
	}
}
