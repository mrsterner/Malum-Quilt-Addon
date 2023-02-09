package dev.sterner.addon.common.entity.ai.brain;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import dev.sterner.addon.common.entity.WillowEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.unmapped.C_lygsomtd;
import net.minecraft.util.math.intprovider.UniformIntProvider;

public class WillowBrain {
	protected static final ImmutableList<SensorType<? extends Sensor<? super WillowEntity>>> SENSORS = ImmutableList.of(
			SensorType.NEAREST_LIVING_ENTITIES,
			SensorType.NEAREST_PLAYERS,
			SensorType.HURT_BY,
			SensorType.NEAREST_ITEMS
	);
	protected static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULES = ImmutableList.of(
			MemoryModuleType.PATH,
			MemoryModuleType.LOOK_TARGET,
			MemoryModuleType.VISIBLE_MOBS,
			MemoryModuleType.WALK_TARGET,
			MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
			MemoryModuleType.HURT_BY,
			MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM
	);


	public static Brain<?> create(WillowEntity willowEntity, Dynamic<?> dynamic) {
		Brain.Profile<WillowEntity> profile = Brain.createProfile(MEMORY_MODULES, SENSORS);
		Brain<WillowEntity> brain = profile.deserialize(dynamic);
		addCoreActivities(brain);
		addIdleActivities(brain);
		addFightActivities(willowEntity, brain);
		brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
		brain.setDefaultActivity(Activity.IDLE);
		brain.resetPossibleActivities();
		return brain;
	}

	private static void addCoreActivities(Brain<WillowEntity> brain) {
		brain.setTaskList(
				Activity.CORE,
				0,
				ImmutableList.of(
						new StayAboveWaterTask(0.8F),
						new WalkTask(2.5F),
						new LookAroundTask(45, 90),
						new WanderAroundTask()
				)
		);
	}

	private static void addIdleActivities(Brain<WillowEntity> brain) {
		brain.setTaskList(
				Activity.IDLE,
				ImmutableList.of(
						Pair.of(3, C_lygsomtd.m_ouvkkubu(6.0F, UniformIntProvider.create(30, 60))),
						Pair.of(4, new RandomTask<>(ImmutableList.of(Pair.of(StrollTask.m_aziirjrs(1.0F), 2), Pair.of(GoTowardsLookTarget.m_ftybsvym(1.0F, 3), 2), Pair.of(new WaitTask(30, 60), 1)))
						)
				),
				ImmutableSet.of()
		);
	}

	private static void addFightActivities(WillowEntity willowEntity, Brain<WillowEntity> brain) {
		brain.setTaskList(
				Activity.FIGHT,
				10,
				ImmutableList.of(
						RangedApproachTask.m_lybdhlji(1.0F),
						FollowMobTask.m_xtjussog(mob -> isTarget(willowEntity, mob), (float)willowEntity.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE)),
						MeleeAttackTask.m_bsseqsar(18)
				),
				MemoryModuleType.ATTACK_TARGET
		);
	}

	public static void updateActivities(WillowEntity willowEntity) {
		willowEntity.getBrain().resetPossibleActivities(ImmutableList.of(Activity.IDLE));
	}

	private static boolean isTarget(WillowEntity willowEntity, LivingEntity entity) {
		return willowEntity.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET).filter(targetedEntity -> targetedEntity == entity).isPresent();
	}
}
