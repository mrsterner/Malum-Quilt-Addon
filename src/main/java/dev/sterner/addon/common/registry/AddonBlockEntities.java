package dev.sterner.addon.common.registry;

import dev.sterner.addon.common.blockentity.BeamBlockEntity;
import dev.sterner.addon.common.blockentity.SpiritCrystalBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.block.entity.api.QuiltBlockEntityTypeBuilder;

import java.util.LinkedHashMap;
import java.util.Map;

import static dev.sterner.malum.Malum.MODID;

public interface AddonBlockEntities {
	Map<Identifier, BlockEntityType<?>> BLOCK_ENTITY_TYPES  = new LinkedHashMap<>();

	BlockEntityType<BeamBlockEntity> BEAM = register("beam", QuiltBlockEntityTypeBuilder.create(BeamBlockEntity::new, AddonObjects.BEAM_BLOCK).build(null));
	BlockEntityType<SpiritCrystalBlockEntity> CRYSTAL_GROUND = register("crystal_ground", QuiltBlockEntityTypeBuilder.create((blockPos, blockState) ->  new SpiritCrystalBlockEntity(blockPos, blockState, null),
			AddonObjects.AERIAL_CRYSTAL,
			AddonObjects.AQUEOUS_CRYSTAL,
			AddonObjects.ARCANE_CRYSTAL,
			AddonObjects.DAMNED_CRYSTAL,
			AddonObjects.ELDRITCH_CRYSTAL,
			AddonObjects.INFERNAL_CRYSTAL,
			AddonObjects.SACRED_CRYSTAL,
			AddonObjects.WICKED_CRYSTAL,
			AddonObjects.EARTHEN_CRYSTAL
	).build(null));


	static <T extends BlockEntity> BlockEntityType<T> register(String id, BlockEntityType<T> type) {
		BLOCK_ENTITY_TYPES.put(new Identifier(MODID, id), type);
		return type;
	}

	static void init() {
		BLOCK_ENTITY_TYPES.forEach((id, entityType) -> Registry.register(Registries.BLOCK_ENTITY_TYPE, id, entityType));
	}
}
