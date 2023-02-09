package dev.sterner.addon.common.entity;

import com.mojang.serialization.Dynamic;
import dev.sterner.addon.common.entity.ai.brain.GolemBrain;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

public class ArcaneGolemEntity extends PathAwareEntity {
	public ArcaneGolemEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	protected void mobTick() {
		ServerWorld serverWorld = (ServerWorld)this.world;
		serverWorld.getProfiler().push("golemBrain");
		this.getBrain().tick(serverWorld, this);
		this.world.getProfiler().pop();
		super.mobTick();
		GolemBrain.updateActivities(this);
	}

	@Override
	protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
		return GolemBrain.create(this, dynamic);
	}

	@Override
	public Brain<ArcaneGolemEntity> getBrain() {
		return (Brain<ArcaneGolemEntity>) super.getBrain();
	}

	public static DefaultAttributeContainer.Builder createAttributes() {
		return MobEntity.createMobAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 100.0)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 10.0);
	}
}
