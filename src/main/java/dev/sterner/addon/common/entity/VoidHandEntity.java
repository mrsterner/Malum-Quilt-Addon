package dev.sterner.addon.common.entity;

import dev.sterner.malum.common.blockentity.VoidConduitBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class VoidHandEntity extends MobEntity implements GeoEntity {
	private static final TrackedData<Integer> SPAWN_TIMER = DataTracker.registerData(VoidHandEntity.class, TrackedDataHandlerRegistry.INTEGER);

	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

	public VoidHandEntity(EntityType<? extends MobEntity> entityType, World world) {
		super(entityType, world);
	}

	public static DefaultAttributeContainer.Builder createAttributes() {
		return MobEntity.createMobAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 200.0)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.0)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5.0);
	}

	@Override
	public void takeKnockback(double strength, double x, double z) {
	}



	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putInt("SpawnTimer", getSpawnTimer());
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		setSpawnTimer(nbt.getInt("SpawnTimer"));
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(SPAWN_TIMER, 75);
	}

	@Override
	public void tick() {
		if (this.getSpawnTimer() > 0) {
			this.setSpawnTimer(getSpawnTimer() - 1);
		}
		super.tick();
	}

	@Override
	public void tickMovement() {
	}

	@Override
	public boolean damage(DamageSource source, float amount) {
		return false;
	}

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controller) {
		controller.add(new AnimationController<>(this, "idle", 20, this::idle).setAnimationSpeed(0.5f));
		controller.add(new AnimationController<>(this, "emerge", 0, this::emerge).setAnimationSpeed(0.5f));
	}

	private PlayState idle(AnimationState<VoidHandEntity> state) {
		if (getSpawnTimer() <= 0) {
			state.setAnimation(RawAnimation.begin().thenLoop("misc.idle"));
			return PlayState.CONTINUE;
		}
		return PlayState.STOP;
	}

	private PlayState emerge(AnimationState<VoidHandEntity> state) {
		if (getSpawnTimer() > 0) {
			state.setAnimation(RawAnimation.begin().then("misc.emerge", Animation.LoopType.HOLD_ON_LAST_FRAME));
			return PlayState.CONTINUE;
		}
		return PlayState.STOP;
	}



	@Override
	protected void playHurtSound(DamageSource source) {

	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return cache;
	}

	public int getSpawnTimer() {
		return this.dataTracker.get(SPAWN_TIMER);
	}

	public void setSpawnTimer(int ticks) {
		this.dataTracker.set(SPAWN_TIMER, ticks);
	}
}
