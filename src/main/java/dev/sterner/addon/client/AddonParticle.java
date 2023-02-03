package dev.sterner.addon.client;

import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.sterner.addon.common.Utils;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.render.Camera;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;

public class AddonParticle extends Particle {
	public int particle = 16;
	ArrayList<Vec3d> points = new ArrayList();
	private Entity targetEntity = null;
	private double targetX = 0.0;
	private double targetY = 0.0;
	private double targetZ = 0.0;
	public float length = 1.0F;

	public AddonParticle(ClientWorld world, double x, double y, double z, double targetX, double targetY, double targetZ, float red, float green, float blue, double heightGain) {
		super(world, x, y, z, 0, 0, 0);
		this.colorRed = red;
		this.colorGreen = green;
		this.colorBlue = blue;
		this.collidesWithWorld = false;
		this.velocityX = 0;
		this.velocityY = 0;
		this.velocityZ = 0;
		this.targetX = targetX - x;
		this.targetY = targetY - y;
		this.targetZ = targetZ - z;
		this.maxAge = 1;
		double xx = 0.0;
		double yy = 0.0;
		double zz = 0.0;
		double gravity = 0.115;
		double noise = 0.25;
		Vec3d vs = new Vec3d(xx, yy, zz);
		Vec3d ve = new Vec3d(this.targetX, this.targetY, this.targetZ);
		Vec3d vc = new Vec3d(xx, yy, zz);
		this.length = (float)ve.length();
		Vec3d vv = Utils.calculateVelocity(vs, ve, heightGain, gravity);
		double l = Utils.distanceSquared3d(new Vec3d(0, 0, 0), vv);
		this.points.add(vs);

		for(int c = 0; Utils.distanceSquared3d(ve, vc) > l && c < 50; ++c) {
			Vec3d vt = vc.multiply(vv.x, vv.y, vv.z);
			vc = new Vec3d(vt.x, vt.y, vt.z);
			vt.add((world.random.nextDouble() - world.random.nextDouble()) * noise, 0, 0);
			vt.add(0, (world.random.nextDouble() - world.random.nextDouble()) * noise, 0);
			vt.add(0,0,(world.random.nextDouble() - world.random.nextDouble()) * noise);
			this.points.add(vt);
			vv.add(0, - gravity / 1.9, 0);
		}

		this.points.add(ve);
	}

	@Override
	public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {

		float size = 0.25F;
		float f9 = 0.0F;
		float f10 = 1.0F;
		int c;
		Vec3d v;
		float f13;
		double dx;
		double dy;
		double dz;
		for(c = 0; c < this.points.size(); ++c) {
			v = this.points.get(c);
			f13 = (float)c / this.length;
			dx = v.x;
			dy = v.y;
			dz = v.z;
			vertexConsumer.vertex(dx, dy - (double)size, dz).uv(f13, f10).next();
			vertexConsumer.vertex(dx, dy + (double)size, dz).uv(f13, f9).next();
		}

		for(c = 0; c < this.points.size(); ++c) {
			v = this.points.get(c);
			f13 = (float)c / this.length;
			dx = v.x;
			dy = v.y;
			dz = v.z;
			vertexConsumer.vertex(dx - (double)size, dy, dz - (double)size).uv(f13, f10).next();
			vertexConsumer.vertex(dx + (double)size, dy, dz + (double)size).uv(f13, f9).next();
		}
	}



	@Override
	public ParticleTextureSheet getType() {
		return ParticleTextureSheet.CUSTOM;
	}
}
