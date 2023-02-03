package dev.sterner.addon.common;

import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public class BeamBlockEntity extends BlockEntity {
	public BeamBlockEntity(BlockPos pos, BlockState state) {
		super(AddonBlockEntities.BEAM, pos, state);
	}


}
