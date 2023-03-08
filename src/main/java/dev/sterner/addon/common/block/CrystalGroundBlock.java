package dev.sterner.addon.common.block;

import com.sammy.lodestone.systems.block.WaterLoggedEntityBlock;
import dev.sterner.addon.common.blockentity.CrystalGroundBlockEntity;
import dev.sterner.addon.common.registry.AddonBlockEntities;
import dev.sterner.addon.common.registry.AddonSpiritTypes;
import dev.sterner.addon.common.util.AddonUtils;
import dev.sterner.malum.common.spirit.MalumSpiritType;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public class CrystalGroundBlock<T extends CrystalGroundBlockEntity> extends WaterLoggedEntityBlock<T> {
	public MalumSpiritType type = AddonSpiritTypes.DAMNED_SPIRIT;
	public static final IntProperty AGE = Properties.AGE_3;
	public static final DirectionProperty FACING = Properties.FACING;

	public static final VoxelShape SHAPE_0, SHAPE_1, SHAPE_2, SHAPE_3;
	static {
		SHAPE_0 = VoxelShapes.union(createCuboidShape(3, 0, 3, 8, 3, 8), createCuboidShape(10, 0, 3, 14, 3, 7), createCuboidShape(2, 0, 9, 6, 2, 13), createCuboidShape(8, 0, 9, 13, 4, 14), createCuboidShape(7, 0, 4, 12, 5, 9), createCuboidShape(6, 0, 6, 11, 6, 11));
		SHAPE_1 = VoxelShapes.union(createCuboidShape(3, 0, 3, 8, 3, 8), createCuboidShape(10, 0, 3, 14, 4, 7), createCuboidShape(2, 0, 9, 6, 3, 13), createCuboidShape(8, 0, 9, 13, 5, 14), createCuboidShape(7, 0, 4, 12, 6, 9), createCuboidShape(6, 0, 6, 11, 10, 11));
		SHAPE_2 = VoxelShapes.union(createCuboidShape(3, 0, 3, 8, 5, 8), createCuboidShape(10, 0, 3, 14, 4, 7), createCuboidShape(2, 0, 9, 6, 3, 13), createCuboidShape(8, 0, 9, 13, 5, 14), createCuboidShape(7, 0, 4, 12, 7, 9), createCuboidShape(6, 0, 6, 11, 12, 11));
		SHAPE_3 = VoxelShapes.union(createCuboidShape(3, 0, 3, 8, 6, 8), createCuboidShape(10, 0, 3, 14, 5, 7), createCuboidShape(2, 0, 9, 6, 3, 13), createCuboidShape(8, 0, 9, 13, 6, 14), createCuboidShape(7, 0, 4, 12, 9, 9), createCuboidShape(6, 0, 6, 11, 14, 11));
	}

	public static final VoxelShape[] SHAPES = new VoxelShape[]{SHAPE_0, SHAPE_1, SHAPE_2, SHAPE_3};
	public static final VoxelShape[][] CACHE = new VoxelShape[DIRECTIONS.length][AGE.getValues().size()];


	public CrystalGroundBlock(Settings settings) {
		super(settings.nonOpaque());
		this.setDefaultState(getDefaultState().with(AGE, 0).with(FACING, Direction.UP));

	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		Direction dir = state.get(FACING);
		int age = state.get(AGE);
		if(CACHE[dir.getId()][age].isEmpty()){
			CACHE[dir.getId()][age] = getShape(state);
		}
		return CACHE[dir.getId()][age];
	}

	private VoxelShape getShape(BlockState state){
		return switch (state.get(FACING)) {
			case UP -> SHAPES[state.get(AGE)];
			case DOWN -> AddonUtils.rotateShape(2, SHAPES[state.get(AGE)], 'x');
			case NORTH -> AddonUtils.rotateShape(3, SHAPES[state.get(AGE)], 'x');
			case SOUTH -> AddonUtils.rotateShape(1, AddonUtils.rotateShape(2, SHAPES[state.get(AGE)], 'y'), 'x');
			case WEST -> AddonUtils.rotateShape(3, AddonUtils.rotateShape(2, AddonUtils.rotateShape(1, SHAPES[state.get(AGE)], 'z'), 'y'), 'x');
			case EAST -> AddonUtils.rotateShape(1, AddonUtils.rotateShape(1, SHAPES[state.get(AGE)], 'z'), 'x');
		};
	}

	@Nullable
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return super.getPlacementState(ctx).with(FACING, ctx.getSide());
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(AGE, FACING);
		super.appendProperties(builder);
	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		setBlockEntity((BlockEntityType<T>) AddonBlockEntities.CRYSTAL_GROUND);
		return new CrystalGroundBlockEntity(pos, state, type);
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

	@Override
	public BlockState rotate(BlockState state, BlockRotation rotation) {
		return state.with(FACING, rotation.rotate(state.get(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, BlockMirror mirror) {
		return state.rotate(mirror.getRotation(state.get(FACING)));
	}

	@Override
	public PistonBehavior getPistonBehavior(BlockState state) {
		return PistonBehavior.DESTROY;
	}


}
