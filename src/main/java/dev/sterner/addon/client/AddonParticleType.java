package dev.sterner.addon.client;

import com.mojang.serialization.Codec;
import net.minecraft.client.option.GameOptions;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;

public class AddonParticleType extends ParticleType {
	/**
	 * @param alwaysShow        whether this particle type should appear regardless of {@linkplain GameOptions#particles particle mode}
	 * @param parametersFactory
	 */
	protected AddonParticleType(boolean alwaysShow, ParticleEffect.Factory parametersFactory) {
		super(alwaysShow, parametersFactory);
	}

	@Override
	public Codec getCodec() {
		return null;
	}
}
