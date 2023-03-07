package dev.sterner.addon.common.util;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import org.quiltmc.qsl.networking.api.PacketByteBufs;

public class AddonUtils {
	public static long setByteInLong(long data, byte b, int index) {
		PacketByteBuf bb = PacketByteBufs.create();
		bb.setLong(0, data);
		bb.setByte(index, b);
		return bb.getLong(0);
	}

	public static byte getByteInLong(long data, int index) {
		PacketByteBuf bb = PacketByteBufs.create();
		bb.setLong(0, data);
		return bb.getByte(index);
	}

	public static VoxelShape rotateShape(int times, VoxelShape shape, char axis) {
		VoxelShape[] buffer = new VoxelShape[]{ shape, VoxelShapes.empty() };
		for (int i = 0; i < times; i++) {
			buffer[0].forEachBox((minX, minY, minZ, maxX, maxY, maxZ) -> {
				switch (axis) {
					case 'x' -> buffer[1] = VoxelShapes.combine(buffer[1],
							VoxelShapes.cuboid(minX, 1 - maxZ, minY, maxX, 1 - minZ, maxY), BooleanBiFunction.OR);
					case 'y' -> buffer[1] = VoxelShapes.combine(buffer[1],
							VoxelShapes.cuboid(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX), BooleanBiFunction.OR);
					case 'z' -> buffer[1] = VoxelShapes.combine(buffer[1],
							VoxelShapes.cuboid(minY, minX, 1 - maxZ, maxY, maxX, 1 - minZ), BooleanBiFunction.OR);
					default -> throw new IllegalArgumentException("Invalid axis argument: " + axis);
				}
			});
			buffer[0] = buffer[1];
			buffer[1] = VoxelShapes.empty();
		}
		return buffer[0];
	}
}
