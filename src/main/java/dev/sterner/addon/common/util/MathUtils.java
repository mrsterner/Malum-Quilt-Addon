package dev.sterner.addon.common.util;

import net.minecraft.util.math.Vec3d;

public class MathUtils {
	public static Vec3d calculateVelocity(Vec3d from, Vec3d to, double heightGain, double gravity) {
		double endGain = to.y - from.y;
		double horizDist = Math.sqrt(distanceSquared2d(from, to));
		double maxGain = heightGain > endGain + heightGain ? heightGain : endGain + heightGain;
		double a = -horizDist * horizDist / (4.0 * maxGain);
		double c = -endGain;
		double slope = -horizDist / (2.0 * a) - Math.sqrt(horizDist * horizDist - 4.0 * a * c) / (2.0 * a);
		double vy = Math.sqrt(maxGain * gravity);
		double vh = vy / slope;
		double dx = to.x - from.x;
		double dz = to.z - from.z;
		double mag = Math.sqrt(dx * dx + dz * dz);
		double dirx = dx / mag;
		double dirz = dz / mag;
		double vx = vh * dirx;
		double vz = vh * dirz;
		return new Vec3d(vx, vy, vz);
	}

	public static double distanceSquared2d(Vec3d from, Vec3d to) {
		double dx = to.x - from.x;
		double dz = to.z - from.z;
		return dx * dx + dz * dz;
	}

	public static double distanceSquared3d(Vec3d from, Vec3d to) {
		double dx = to.x - from.x;
		double dy = to.y - from.y;
		double dz = to.z - from.z;
		return dx * dx + dy * dy + dz * dz;
	}

	public static float zeroToOne(float time, float strength){
		return time * strength % 1.0F;
	}
}
