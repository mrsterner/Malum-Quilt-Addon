package dev.sterner.addon.common.registry;

import dev.sterner.addon.Addon;
import dev.sterner.addon.common.block.BeamBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;

import java.util.LinkedHashMap;
import java.util.Map;

public interface AddonObjects {
	Map<Block, Identifier> BLOCKS = new LinkedHashMap<>();
	Map<Item, Identifier> ITEMS = new LinkedHashMap<>();

	Block BEAM_BLOCK = register("beam_block", new BeamBlock(QuiltBlockSettings.copyOf(Blocks.STONE)), true);

	static <T extends Block> T register(String name, T block, boolean createItem) {
		BLOCKS.put(block, Addon.id(name));
		if (createItem) {
			ITEMS.put(new BlockItem(block, settings()), BLOCKS.get(block));
		}
		return block;
	}

	static <T extends Item> T register(String name, T item) {
		ITEMS.put(item, Addon.id(name));
		return item;
	}

	static Item.Settings settings() {
		return new Item.Settings();
	}

	static void init() {
		BLOCKS.keySet().forEach(block -> Registry.register(Registries.BLOCK, BLOCKS.get(block), block));
		ITEMS.keySet().forEach(item -> Registry.register(Registries.ITEM, ITEMS.get(item), item));
	}
}
