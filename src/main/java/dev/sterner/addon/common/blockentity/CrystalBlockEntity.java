package dev.sterner.addon.common.blockentity;

import com.sammy.lodestone.setup.LodestoneParticleRegistry;
import com.sammy.lodestone.systems.blockentity.LodestoneBlockEntity;
import com.sammy.lodestone.systems.particle.WorldParticleBuilder;
import com.sammy.lodestone.systems.particle.data.ColorParticleData;
import com.sammy.lodestone.systems.particle.data.GenericParticleData;
import com.sammy.lodestone.systems.particle.data.SpinParticleData;
import dev.sterner.addon.common.registry.AddonBlockEntities;
import dev.sterner.addon.common.registry.AddonSpiritTypes;
import dev.sterner.malum.common.spirit.MalumSpiritType;
import dev.sterner.malum.common.spirit.SpiritHelper;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

import java.awt.*;

public class CrystalBlockEntity extends LodestoneBlockEntity {
	public int MAX_POWER = 256;
	public int power = 0;
	public MalumSpiritType type;
	public boolean loop = true;

	public CrystalBlockEntity(BlockPos pos, BlockState state, MalumSpiritType type) {
		super(AddonBlockEntities.CRYSTAL, pos, state);
		this.type = type;
	}

	@Override
	public void tick() {
		super.tick();
		if(type == null){
			type = AddonSpiritTypes.DAMNED_SPIRIT;
		}
		if(loop){
			if(power > MAX_POWER){
				loop = false;
			}
			power++;
		}else{
			if(power <= 0){
				loop = true;
			}
			power--;
		}
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		if (nbt.contains("spirit")) {
			type = SpiritHelper.getSpiritType(nbt.getString("spirit"));
		} else {
			type = null;
		}
		if(nbt.contains("power")) {
			power = nbt.getInt("power");
		} else {
			power = 0;
		}
		super.readNbt(nbt);
	}

	@Override
	protected void writeNbt(NbtCompound nbt) {
		if (type != null) {
			nbt.putString("spirit", type.identifier);
		}
		if(power != 0){
			nbt.putInt("power", power);
		}
		super.writeNbt(nbt);
	}

	@Override
	public void clientTick() {
		super.clientTick();
		if(power >= MAX_POWER / 2){
			Color color = type.getColor();
			WorldParticleBuilder.create(LodestoneParticleRegistry.TWINKLE_PARTICLE)
					.setTransparencyData(GenericParticleData.create(0.15f, 0f).build())
					.setScaleData(GenericParticleData.create(0.3f, 0).build())
					.setSpinData(SpinParticleData.create(0.2f).build())
					.setColorData(ColorParticleData.create(color, color.darker()).build())
					.setLifetime(20)
					.setRandomMotion(0.02f)
					.setRandomOffset(0.1f, 0.1f)
					.enableNoClip()
					.repeat(world, pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, 4 * (1 + (int)(power / MAX_POWER)));
		}


	}
}
