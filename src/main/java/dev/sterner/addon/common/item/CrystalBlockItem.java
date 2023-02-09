package dev.sterner.addon.common.item;

import dev.sterner.malum.common.spirit.MalumSpiritType;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;

public class CrystalBlockItem extends BlockItem {
	public MalumSpiritType type;
	public CrystalBlockItem(Block block, Settings settings, MalumSpiritType type) {
		super(block, settings);
		this.type = type;
	}
}
