package dev.sterner.addon.common.registry;

import dev.sterner.addon.Addon;
import dev.sterner.addon.common.entity.EffigyEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

public class AddonEntityTypes {
	private static final Map<EntityType<?>, Identifier> ENTITY_TYPES = new LinkedHashMap<>();


	public static final EntityType<EffigyEntity> EFFIGY = register("effigy", FabricEntityTypeBuilder
			.<EffigyEntity>createLiving()
			.entityFactory(EffigyEntity::new)
			.dimensions(EntityDimensions.fixed(0.6F, 1.8F))
			.defaultAttributes(EffigyEntity::createAttributes)
			.build());


	private static <T extends Entity> EntityType<T> register(String name, EntityType<T> type) {
		ENTITY_TYPES.put(type, Addon.id(name));
		return type;
	}

	public static void init() {
		ENTITY_TYPES.keySet().forEach(entityType -> Registry.register(Registries.ENTITY_TYPE, ENTITY_TYPES.get(entityType), entityType));
	}
}
