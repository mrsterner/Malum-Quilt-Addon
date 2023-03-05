package dev.sterner.addon.common.util;

import net.minecraft.network.PacketByteBuf;
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
}
