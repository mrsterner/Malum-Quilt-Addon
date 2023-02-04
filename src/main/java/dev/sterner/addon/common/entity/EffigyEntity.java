package dev.sterner.addon.common.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class EffigyEntity extends LivingEntity {
	private final DefaultedList<ItemStack> handItems;
	private final DefaultedList<ItemStack> armorItems;

	public EffigyEntity(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
		this.handItems = DefaultedList.ofSize(2, ItemStack.EMPTY);
		this.armorItems = DefaultedList.ofSize(4, ItemStack.EMPTY);
	}

	@Override
	public Iterable<ItemStack> getArmorItems() {
		return this.armorItems;
	}

	@Override
	public ItemStack getEquippedStack(EquipmentSlot slot) {
		return switch (slot.getType()) {
			case HAND -> this.handItems.get(slot.getEntitySlotId());
			case ARMOR -> this.armorItems.get(slot.getEntitySlotId());
		};
	}

	@Override
	protected boolean isImmobile() {
		return true;
	}

	public static DefaultAttributeContainer.Builder createAttributes() {
		return HostileEntity.createHostileAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 100.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, Double.MAX_VALUE);
	}

	@Override
	public void equipStack(EquipmentSlot slot, ItemStack stack) {
		this.processEquippedStack(stack);
		switch (slot.getType()) {
			case HAND -> this.handItems.set(slot.getEntitySlotId(), stack);
			case ARMOR -> this.armorItems.set(slot.getEntitySlotId(), stack);
		}
	}

	@Override
	public Arm getMainArm() {
		return Arm.RIGHT;
	}
}