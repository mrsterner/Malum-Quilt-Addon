package dev.sterner.addon;

import dev.sterner.addon.common.registry.AddonBlockEntities;
import dev.sterner.addon.common.registry.AddonEntityTypes;
import dev.sterner.addon.common.registry.AddonObjects;
import dev.sterner.addon.common.registry.AddonSpiritTypes;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Addon implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("Addon");
	public static final String MODID = "addon";
	public static final ItemGroup MALUM_ADDON = FabricItemGroup.builder(new Identifier(MODID, MODID)).icon(() -> new ItemStack(AddonObjects.HALLOWED_GOGGLES)).build();


	@Override
	public void onInitialize(ModContainer mod) {
		AddonSpiritTypes.init();
		AddonObjects.init();
		AddonBlockEntities.init();
		AddonEntityTypes.init();


		ItemGroupEvents.modifyEntriesEvent(MALUM_ADDON).register(entries -> {
			entries.addItem(AddonObjects.HALLOWED_GOGGLES);
			entries.addItem(AddonObjects.DAMNED_SPIRIT);
			entries.addItem(AddonObjects.AERIAL_CRYSTAL);
			entries.addItem(AddonObjects.AQUEOUS_CRYSTAL);
			entries.addItem(AddonObjects.ARCANE_CRYSTAL);
			entries.addItem(AddonObjects.DAMNED_CRYSTAL);
			entries.addItem(AddonObjects.EARTHEN_CRYSTAL);
			entries.addItem(AddonObjects.ELDRITCH_CRYSTAL);
			entries.addItem(AddonObjects.INFERNAL_CRYSTAL);
			entries.addItem(AddonObjects.SACRED_CRYSTAL);
			entries.addItem(AddonObjects.WICKED_CRYSTAL);
		});
	}

	public static Identifier id(String name){
		return new Identifier(MODID, name);
	}
}
