package dev.sterner.addon.common.block;

import com.sammy.lodestone.systems.block.WaterLoggedEntityBlock;
import dev.sterner.addon.common.blockentity.CrystalBlockEntity;
import dev.sterner.addon.common.blockentity.CrystalGroundBlockEntity;
import dev.sterner.addon.common.registry.AddonBlockEntities;
import dev.sterner.addon.common.registry.AddonSpiritTypes;
import dev.sterner.malum.common.spirit.MalumSpiritType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CrystalGroundBlock<T extends CrystalGroundBlockEntity> extends WaterLoggedEntityBlock<T> {
	public MalumSpiritType type = AddonSpiritTypes.DAMNED_SPIRIT;
	public CrystalGroundBlock(Settings settings) {
		super(settings.nonOpaque());
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
			return BlockRenderType.INVISIBLE;
			}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		setBlockEntity((BlockEntityType<T>) AddonBlockEntities.CRYSTAL_GROUND);
		return new CrystalGroundBlockEntity(pos, state, type);
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return Block.createCuboidShape(6.5,5,6.5,9.5,12,9.5);
	}

	@Override
	public @Nullable <B extends BlockEntity> BlockEntityTicker<B> getTicker(@NotNull World world, @NotNull BlockState state, @NotNull BlockEntityType<B> type) {
		return  (tickerWorld, pos, tickerState, blockEntity) -> {
			if(blockEntity instanceof CrystalGroundBlockEntity be){
				be.tick();
				if(world.isClient()){
					be.clientTick();
				}else{
					be.serverTick();
				}
			}
		};
	}
}
