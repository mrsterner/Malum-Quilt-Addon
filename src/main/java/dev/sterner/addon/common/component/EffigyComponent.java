package dev.sterner.addon.common.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import dev.sterner.addon.common.entity.EffigyEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;

import java.util.Map;
import java.util.UUID;

public class EffigyComponent implements AutoSyncedComponent, ServerTickingComponent {
	private UUID effigyEntity = null;
	private final PlayerEntity player;
	private boolean hasEffigy = false;

	public EffigyComponent(PlayerEntity player) {
		this.player = player;
	}

	@Override
	public void serverTick() {
		if (effigyEntity != null) {
			Entity entity = ((ServerWorld) player.world).getEntity(getEffigy());
			if(entity instanceof EffigyEntity effigy){
				effigy.setPlayerUuid(player.getUuid());
				if(!effigy.getStatusEffects().isEmpty()){
					Map<StatusEffect, StatusEffectInstance> statusEffectsEffigy = effigy.getActiveStatusEffects();
					statusEffectsEffigy.forEach((statusEffect, statusEffectInstance) -> player.addStatusEffect(statusEffectInstance));
				}
				ServerWorld serverWorld = (ServerWorld) effigy.world;
				serverWorld.setChunkForced(effigy.getChunkPos().x, effigy.getChunkPos().z,true);
			}
		}
	}

	public void hasEffigy(boolean hasEffigy){
		this.hasEffigy = hasEffigy;
	}

	public boolean hasEffigy(){
		return hasEffigy;
	}

	public UUID getEffigy() {
		return effigyEntity;
	}

	public void setEffigy(UUID effigyEntity) {
		this.effigyEntity = effigyEntity;
		AddonComponents.EFFIGY_COMPONENT.sync(player);
	}


	@Override
	public void readFromNbt(NbtCompound tag) {
		setEffigy(tag.getString("effigyUUID").isEmpty() ? null : UUID.fromString(tag.getString("effigyUUID")));
	}

	@Override
	public void writeToNbt(NbtCompound tag) {
		tag.putString("effigyUUID", getEffigy() == null ? "" : getEffigy().toString());
	}
}
