package dev.sterner.addon.common.registry;

import dev.sterner.malum.common.registry.MalumObjects;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class AddonMaterials {
	public AddonMaterials() {
	}

	public static enum ArmorMaterialEnum implements ArmorMaterial {
		HALLOWED("malum:hallowed", 16, new int[]{1, 3, 4, 2}, 25, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, MalumObjects.HALLOWED_GOLD_INGOT, 0.0F);

		private final String name;
		private final int durabilityMultiplier;
		private final int[] damageReduction;
		private final int enchantability;
		private final SoundEvent equipSound;
		private final Item repairItem;
		private final float toughness;
		private static final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};

		private ArmorMaterialEnum(String name, int durabilityMultiplier, int[] damageReduction, int enchantability, SoundEvent equipSound, Item repairItem, float toughness) {
			this.name = name;
			this.durabilityMultiplier = durabilityMultiplier;
			this.damageReduction = damageReduction;
			this.enchantability = enchantability;
			this.equipSound = equipSound;
			this.repairItem = repairItem;
			this.toughness = toughness;
		}

		public int getDurability(EquipmentSlot slot) {
			return this.durabilityMultiplier * MAX_DAMAGE_ARRAY[slot.getEntitySlotId()];
		}

		public int getProtectionAmount(EquipmentSlot slot) {
			return this.damageReduction[slot.getEntitySlotId()];
		}

		public int getEnchantability() {
			return this.enchantability;
		}

		public SoundEvent getEquipSound() {
			return this.equipSound;
		}

		public Ingredient getRepairIngredient() {
			return Ingredient.ofItems(this.repairItem);
		}

		public String getName() {
			return this.name;
		}

		public float getToughness() {
			return this.toughness;
		}

		public float getKnockbackResistance() {
			return 0.0F;
		}
	}
}
