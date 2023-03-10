package dev.sterner.addon.common.blockentity;

import com.sammy.lodestone.systems.blockentity.LodestoneBlockEntity;
import dev.sterner.addon.common.registry.AddonBlockEntities;
import dev.sterner.addon.common.registry.AddonSpiritTypes;
import dev.sterner.malum.common.spirit.MalumSpiritType;
import dev.sterner.malum.common.spirit.SpiritHelper;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public class SpiritCrystalBlockEntity extends LodestoneBlockEntity {
	public int MAX_POWER = 256;
	public int power = 0;
	public MalumSpiritType type;
	public boolean loop = true;

	public SpiritCrystalBlockEntity(BlockPos pos, BlockState state, MalumSpiritType type) {
		super(AddonBlockEntities.CRYSTAL_GROUND, pos, state);
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
		if (nbt.contains("Spirit")) {
			type = SpiritHelper.getSpiritType(nbt.getString("Spirit"));
		} else {
			type = null;
		}
		if(nbt.contains("Power")) {
			power = nbt.getInt("Power");
		} else {
			power = 0;
		}
		super.readNbt(nbt);
	}

	@Override
	protected void writeNbt(NbtCompound nbt) {
		if (type != null) {
			nbt.putString("Spirit", type.identifier);
		}
		if(power != 0){
			nbt.putInt("Power", power);
		}
		super.writeNbt(nbt);
	}

	@Override
	public void clientTick() {
		super.clientTick();
		/*
		if(power >= MAX_POWER / 2){
			Color color = type.getColor();
			int offset = this.getCachedState().get(CrystalGroundBlock.AGE);
			WorldParticleBuilder.create(LodestoneParticleRegistry.TWINKLE_PARTICLE)
					.setTransparencyData(GenericParticleData.create(0.15f, 0f).build())
					.setScaleData(GenericParticleData.create(0.3f, 0).build())
					.setSpinData(SpinParticleData.create(0.2f).build())
					.setColorData(ColorParticleData.create(color, color.darker()).build())
					.setLifetime(20)
					.setRandomMotion(0.02f)
					.setRandomOffset(0.1f, 0.1f)
					.enableNoClip()
					.repeat(world, pos.getX() + 0.5f, pos.getY() + 0.15f * offset, pos.getZ() + 0.5f, 5 * (1 + (power / MAX_POWER)));
		}
		 */
	}
}
