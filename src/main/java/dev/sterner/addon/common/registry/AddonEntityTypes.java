package dev.sterner.addon.common.registry;

import dev.sterner.addon.Addon;
import dev.sterner.addon.common.entity.ArcaneGolemEntity;
import dev.sterner.addon.common.entity.EffigyEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.entity.api.QuiltEntityTypeBuilder;

import java.util.LinkedHashMap;
import java.util.Map;

public interface AddonEntityTypes {
	Map<EntityType<?>, Identifier> ENTITY_TYPES = new LinkedHashMap<>();


	EntityType<EffigyEntity> EFFIGY = register("effigy", QuiltEntityTypeBuilder
			.<EffigyEntity>createLiving()
			.entityFactory(EffigyEntity::new)
			.setDimensions(EntityDimensions.fixed(0.6F, 1.8F))
			.defaultAttributes(EffigyEntity.createAttributes())
			.build());

	EntityType<ArcaneGolemEntity> ARCANE_GOLEM = register("arcane_golem", QuiltEntityTypeBuilder
			.<ArcaneGolemEntity>createLiving()
			.entityFactory(ArcaneGolemEntity::new)
			.setDimensions(EntityDimensions.fixed(0.6F, 1.8F))
			.defaultAttributes(ArcaneGolemEntity.createAttributes())
			.build());

	static <T extends Entity> EntityType<T> register(String name, EntityType<T> type) {
		ENTITY_TYPES.put(type, Addon.id(name));
		return type;
	}

	static void init() {
		ENTITY_TYPES.keySet().forEach(entityType -> Registry.register(Registries.ENTITY_TYPE, ENTITY_TYPES.get(entityType), entityType));
	}
}
