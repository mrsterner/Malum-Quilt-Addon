package dev.sterner.addon.common.entity.ai.brain;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import dev.sterner.addon.common.entity.golem.ArcaneGolemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.unmapped.C_rcqaryar;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorldAccess;

import java.util.List;
import java.util.Optional;

public class GolemBrain {
	private static final List<SensorType<? extends Sensor<? super ArcaneGolemEntity>>> SENSORS = List.of(
			SensorType.NEAREST_PLAYERS,
			SensorType.NEAREST_LIVING_ENTITIES,
			SensorType.HURT_BY);
	private static final List<MemoryModuleType<?>> MEMORIES = List.of(
			MemoryModuleType.MOBS,
			MemoryModuleType.VISIBLE_MOBS,
			MemoryModuleType.NEAREST_VISIBLE_PLAYER,
			MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER,
			MemoryModuleType.LOOK_TARGET,
			MemoryModuleType.WALK_TARGET,
			MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
			MemoryModuleType.PATH,
			MemoryModuleType.ANGRY_AT,
			MemoryModuleType.ATTACK_TARGET,
			MemoryModuleType.ATTACK_COOLING_DOWN,
			MemoryModuleType.NEAREST_ATTACKABLE,
			MemoryModuleType.HOME,
			MemoryModuleType.PACIFIED,
			MemoryModuleType.NEAREST_REPELLENT,
			MemoryModuleType.AVOID_TARGET
	);

	public GolemBrain() {
	}

	public static Brain<?> create(ArcaneGolemEntity arcaneGolem, Dynamic<?> dynamic) {
		Brain.Profile<ArcaneGolemEntity> profile = Brain.createProfile(MEMORIES, SENSORS);
		Brain<ArcaneGolemEntity> brain = profile.deserialize(dynamic);
		addCoreActivities(brain);
		addIdleActivities(brain);
		addFightActivities(arcaneGolem, brain);
		brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
		brain.setDefaultActivity(Activity.IDLE);
		brain.resetPossibleActivities();
		return brain;
	}

	private static void addCoreActivities(Brain<ArcaneGolemEntity> brain) {
		brain.setTaskList(
				Activity.CORE,
				0,
				ImmutableList.of(
						new StayAboveWaterTask(0.6f),
						new LookAroundTask(45, 90),
						new WanderAroundTask(),
						UpdateAttackTargetTask.m_cstouyov(GolemBrain::getAttackTarget)
				)
		);
	}

	private static void addIdleActivities(Brain<ArcaneGolemEntity> brain) {
		brain.setTaskList(
				Activity.IDLE,
				ImmutableList.of(
						Pair.of(0 , GoToRememberedPositionTask.toBlock(MemoryModuleType.NEAREST_REPELLENT, 1.0F, 8, true)),
						Pair.of(1, new RandomTask<>(
								ImmutableList.of(
										Pair.of(StrollTask.m_aziirjrs(0.6F), 2),
										Pair.of(C_rcqaryar.m_ipranrme(livingEntity -> true, GoTowardsLookTarget.m_ftybsvym(0.6F, 3)), 2),
										Pair.of(new WaitTask(30, 60), 1)
								)))
				)
		);
	}

	private static void addFightActivities(ArcaneGolemEntity golemEntity, Brain<ArcaneGolemEntity> brain) {
		brain.setTaskList(
				Activity.FIGHT,
				10,
				ImmutableList.of(
						RangedApproachTask.m_lybdhlji(1.0F),
						FollowMobTask.m_xtjussog(mob -> isTarget(golemEntity, mob), (float)golemEntity.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE)),
						MeleeAttackTask.m_bsseqsar(18)
				),
				MemoryModuleType.ATTACK_TARGET
		);
	}

	public static void updateActivities(ArcaneGolemEntity golemEntity) {
		golemEntity.getBrain().resetPossibleActivities(ImmutableList.of(Activity.FIGHT, Activity.IDLE));
		golemEntity.setAttacking(golemEntity.getBrain().hasMemoryModule(MemoryModuleType.ATTACK_TARGET));
	}

	private static boolean isTarget(ArcaneGolemEntity golemEntity, LivingEntity entity) {
		return golemEntity.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET).filter(targetedEntity -> targetedEntity == entity).isPresent();
	}

	private static Optional<? extends LivingEntity> getAttackTarget(ArcaneGolemEntity golemEntity) {
		Brain<ArcaneGolemEntity> brain = golemEntity.getBrain();
		Optional<LivingEntity> optional = LookTargetUtil.getEntity(golemEntity, MemoryModuleType.ANGRY_AT);

		return Optional.empty();
	}

	//TODO add memory module for home
    public static void setHome(ServerWorldAccess world, BlockPos blockPos) {

    }
}
