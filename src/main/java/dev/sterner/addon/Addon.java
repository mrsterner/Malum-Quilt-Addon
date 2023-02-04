package dev.sterner.addon;

import dev.sterner.addon.common.registry.AddonBlockEntities;
import dev.sterner.addon.common.registry.AddonObjects;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Addon implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("Addon");
	public static final String MODID = "addon";

	@Override
	public void onInitialize(ModContainer mod) {
		AddonObjects.init();
		AddonBlockEntities.init();
	}

	public static Identifier id(String name){
		return new Identifier(MODID, name);
	}
}
