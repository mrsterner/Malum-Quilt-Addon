package dev.sterner.addon.common.entity.golem;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.ServerConfigHandler;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public class OwnedEntity extends PathAwareEntity {
	public static final TrackedData<Byte> TAMED = DataTracker.registerData(OwnedEntity.class, TrackedDataHandlerRegistry.BYTE);
	public static final TrackedData<Optional<UUID>> OWNER_UUID = DataTracker.registerData(OwnedEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);

	public OwnedEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.getDataTracker().startTracking(TAMED,  (byte) 0);
		this.getDataTracker().startTracking(OWNER_UUID, Optional.empty());
	}

	public boolean isOwned() {
		return (this.getDataTracker().get(TAMED) & 4) != 0;
	}

	public void setOwned(boolean tamed) {
		byte b0 = this.getDataTracker().get(TAMED);
		if (tamed) {
			this.getDataTracker().set(TAMED, (byte)(b0 | 4));
		} else {
			this.getDataTracker().set(TAMED, (byte)(b0 & -5));
		}
	}

	@Nullable
	public UUID getOwnerUuid() {
		return this.getDataTracker().get(OWNER_UUID).orElse(null);
	}

	public void setOwnerUuid(UUID uuid) {
		this.getDataTracker().set(OWNER_UUID, Optional.ofNullable(uuid));
	}

	@Override
	public boolean cannotDespawn() {
		return true;
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		if (this.getOwnerUuid() != null) {
			nbt.putUuid("Owner", this.getOwnerUuid());
		}
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		UUID ownerUUID;
		if (nbt.containsUuid("Owner")) {
			ownerUUID = nbt.getUuid("Owner");
		} else {
			String string = nbt.getString("Owner");
			ownerUUID = ServerConfigHandler.getPlayerUuidByName(this.getServer(), string);
		}

		if (ownerUUID != null) {
			try {
				this.setOwnerUuid(ownerUUID);
				this.setOwned(true);
			} catch (Throwable var4) {
				this.setOwned(false);
			}
		}
	}

	@Nullable
	public LivingEntity getOwnerEntity() {
		try {
			UUID uuid = this.getOwnerUuid();
			return uuid == null ? null : this.world.getPlayerByUuid(uuid);
		} catch (IllegalArgumentException var2) {
			return null;
		}
	}

	public boolean isOwner(LivingEntity entityIn) {
		return entityIn == this.getOwnerEntity();
	}
}
