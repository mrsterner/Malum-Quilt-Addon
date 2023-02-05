package dev.sterner.addon.common.blockentity;

import com.sammy.lodestone.setup.LodestoneParticles;
import com.sammy.lodestone.systems.blockentity.LodestoneBlockEntity;
import com.sammy.lodestone.systems.rendering.particle.Easing;
import com.sammy.lodestone.systems.rendering.particle.ParticleBuilders;
import dev.sterner.addon.common.registry.AddonBlockEntities;
import net.fabricmc.fabric.impl.client.indigo.renderer.helper.ColorHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

import java.awt.*;

public class CrystalBlockEntity extends LodestoneBlockEntity {
	public Color color;
	public CrystalBlockEntity(BlockPos pos, BlockState state, Color color) {
		super(AddonBlockEntities.CRYSTAL, pos, state);
		this.color = color;
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		this.color = new Color(nbt.getInt("Color"));
		super.readNbt(nbt);
	}

	@Override
	protected void writeNbt(NbtCompound nbt) {
		nbt.putInt("Color", color.getRGB());
		super.writeNbt(nbt);
	}

	@Override
	public void clientTick() {
		super.clientTick();
		int lifeTime = 14 + world.random.nextInt(4);
		float scale = 0.17f + world.random.nextFloat() * 0.03f;
		float velocity = 0.04f + world.random.nextFloat() * 0.02f;
		double x = pos.getX() + 0.5;
		double y = pos.getY() + 0.6;
		double z = pos.getZ() + 0.5;
		ParticleBuilders.create(LodestoneParticles.SPARKLE_PARTICLE)
				.setScale(scale * 2, 0)
				.setLifetime(lifeTime)
				.setAlpha(0.2f)
				.setColor(color, color)
				.setColorCoefficient(1.5f)
				.setAlphaCoefficient(1.5f)
				.setSpin(0, 2)
				.setSpinEasing(Easing.QUARTIC_IN)
				.enableNoClip()
				.spawn(world, x, y, z);
	}
}
