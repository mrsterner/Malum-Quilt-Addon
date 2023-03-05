package dev.sterner.addon.common.entity.golem;

import com.mojang.serialization.Dynamic;
import dev.sterner.addon.Addon;
import dev.sterner.addon.common.entity.ai.brain.GolemBrain;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.qsl.networking.api.PacketByteBufs;

public class ArcaneGolemEntity extends OwnedEntity {
	public static final TrackedData<Integer> PROPERTY_1 = DataTracker.registerData(ArcaneGolemEntity.class, TrackedDataHandlerRegistry.INTEGER);
	public static final TrackedData<Integer> PROPERTY_2 = DataTracker.registerData(ArcaneGolemEntity.class, TrackedDataHandlerRegistry.INTEGER);

	public static final TrackedData<Integer> XP = DataTracker.registerData(ArcaneGolemEntity.class, TrackedDataHandlerRegistry.INTEGER);

	public ArcaneGolemEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.getDataTracker().startTracking(PROPERTY_1, 0);
		this.getDataTracker().startTracking(PROPERTY_2, 0);
		this.getDataTracker().startTracking(XP, 0);
	}

	public IGolemProperties getProperties(){
		PacketByteBuf buf = PacketByteBufs.create();
		buf.writeInt(this.getDataTracker().get(PROPERTY_1));
		buf.writeInt(this.getDataTracker().get(PROPERTY_2));
		return GolemProperties.fromLong(buf.getLong(0));
	}

	@Nullable
	@Override
	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
		GolemBrain.setHome(world, this.getBlockPos());
		this.updateEntityAttributes();
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	//TODO change attributes depending on property and golemmaterial
	private void updateEntityAttributes() {
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
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 10.0);
	}

	public class GolemMaterial{
		protected static GolemMaterial[] materials = new GolemMaterial[1];
		public Identifier texture;
		public byte id;
		public int healthMod;
		public int armor;
		public int damage;
		public GolemMaterial(String key, String[] research, Identifier texture, int itemColor, int hp, int armor, int damage) {
			this.texture = texture;
			this.healthMod = hp;
			this.armor = armor;
			this.damage = damage;
		}

		public static GolemMaterial[] getMaterials() {
			return materials;
		}
	}

	public enum GolemTrait{
		FARMER(Addon.id("textures/misc/golem/farmer.png")),
		GUARD(Addon.id("textures/misc/golem/farmer.png"));

		public Identifier icon;

		GolemTrait(Identifier icon) {
			this.icon = icon;
		}
	}
}
