package dev.sterner.addon;

import dev.sterner.addon.common.AddonBlockEntities;
import dev.sterner.addon.common.AddonObjects;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Addon implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("Addon");

	@Override
	public void onInitialize(ModContainer mod) {
		AddonObjects.init();
		AddonBlockEntities.init();
	}
}
