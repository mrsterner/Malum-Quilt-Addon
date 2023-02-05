package dev.sterner.addon.common.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public class EffigyEntity extends LivingEntity {
	public static final TrackedData<Optional<UUID>> PLAYER = DataTracker.registerData(EffigyEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);

	private final DefaultedList<ItemStack> handItems;
	private final DefaultedList<ItemStack> armorItems;

	public EffigyEntity(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
		this.handItems = DefaultedList.ofSize(2, ItemStack.EMPTY);
		this.armorItems = DefaultedList.ofSize(4, ItemStack.EMPTY);
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(PLAYER, Optional.empty());
	}

	public UUID getPLayerUuid() {
		return this.dataTracker.get(PLAYER).orElse(null);
	}

	public void setPlayerUuid(@Nullable UUID uuid) {
		this.dataTracker.set(PLAYER, Optional.ofNullable(uuid));
	}

	@Override
	public boolean damage(DamageSource source, float amount) {
		boolean damage = super.damage(source, amount);
		if(damage && getPLayerUuid() != null){
			PlayerEntity player = world.getPlayerByUuid(getPLayerUuid());
			if(player != null){
				player.damage(source, MathHelper.clamp(amount, 0, amount / 4));
			}
		}
		return damage;
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
