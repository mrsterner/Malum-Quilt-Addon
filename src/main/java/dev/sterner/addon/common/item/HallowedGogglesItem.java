package dev.sterner.addon.common.item;

import dev.sterner.addon.common.registry.AddonMaterials;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;

public class HallowedGogglesItem extends ArmorItem {
	public HallowedGogglesItem(Settings settings) {
		super(AddonMaterials.ArmorMaterialEnum.HALLOWED, EquipmentSlot.HEAD, settings);

	}
}
