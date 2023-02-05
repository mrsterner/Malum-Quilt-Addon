package dev.sterner.addon.common.registry;

import dev.sterner.addon.Addon;
import dev.sterner.addon.common.block.BeamBlock;
import dev.sterner.addon.common.block.CrystalBlock;
import dev.sterner.addon.common.item.CrystalBlockItem;
import dev.sterner.addon.common.item.HallowedGogglesItem;
import dev.sterner.malum.common.registry.MalumSpiritTypeRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public interface AddonObjects {
	Map<Block, Identifier> BLOCKS = new LinkedHashMap<>();
	Map<Item, Identifier> ITEMS = new LinkedHashMap<>();

	Item HALLOWED_GOGGLES = register("hallowed_goggles", new HallowedGogglesItem(settings()));
	Block BEAM_BLOCK = register("beam_block", new BeamBlock(QuiltBlockSettings.copyOf(Blocks.STONE)), true);

	Block ARCANE_CRYSTAL = registerCrystal("arcane_crystal", new CrystalBlock(QuiltBlockSettings.copyOf(Blocks.AMETHYST_BLOCK), MalumSpiritTypeRegistry.ARCANE_SPIRIT.getColor()));
	Block INFERNAL_CRYSTAL = registerCrystal("infernal_crystal", new CrystalBlock(QuiltBlockSettings.copyOf(Blocks.AMETHYST_BLOCK), MalumSpiritTypeRegistry.INFERNAL_SPIRIT.getColor()));


	static CrystalBlock registerCrystal(String name, CrystalBlock block) {
		BLOCKS.put(block, Addon.id(name));
		ITEMS.put(new CrystalBlockItem(block, settings(), block.color), BLOCKS.get(block));
		return block;
	}

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
