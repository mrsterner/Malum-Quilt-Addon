package dev.sterner.addon.common.registry;

import dev.sterner.malum.common.registry.MalumObjects;
import dev.sterner.malum.common.registry.MalumSpiritTypeRegistry;
import dev.sterner.malum.common.spirit.MalumSpiritType;

import java.awt.Color;

public interface AddonSpiritTypes {

	MalumSpiritType DAMNED_SPIRIT = MalumSpiritTypeRegistry.create("damned", new Color(175, 0, 42), MalumObjects.SACRED_SPIRIT);

	static void init(){

	}
}
