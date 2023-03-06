package dev.sterner.addon.common.registry;

import dev.sterner.addon.common.blockentity.BeamBlockEntity;
import dev.sterner.addon.common.blockentity.CrystalBlockEntity;
import dev.sterner.addon.common.blockentity.CrystalGroundBlockEntity;
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
	BlockEntityType<CrystalBlockEntity> CRYSTAL = register("crystal", QuiltBlockEntityTypeBuilder.create((blockPos, blockState) ->  new CrystalBlockEntity(blockPos, blockState, null), AddonObjects.SPIRIT_CRYSTAL).build(null));
	BlockEntityType<CrystalGroundBlockEntity> CRYSTAL_GROUND = register("crystal_ground", QuiltBlockEntityTypeBuilder.create((blockPos, blockState) ->  new CrystalGroundBlockEntity(blockPos, blockState, null), AddonObjects.SPIRIT_CRYSTAL_GROUND).build(null));


	static <T extends BlockEntity> BlockEntityType<T> register(String id, BlockEntityType<T> type) {
		BLOCK_ENTITY_TYPES.put(new Identifier(MODID, id), type);
		return type;
	}

	static void init() {
		BLOCK_ENTITY_TYPES.forEach((id, entityType) -> Registry.register(Registries.BLOCK_ENTITY_TYPE, id, entityType));
	}
}
