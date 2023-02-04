package dev.sterner.addon.common.component;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.sterner.addon.Addon;
import net.minecraft.entity.player.PlayerEntity;

public class AddonComponents implements EntityComponentInitializer {
	public static final ComponentKey<EffigyComponent> EFFIGY_COMPONENT = ComponentRegistryV3.INSTANCE.getOrCreate(Addon.id("effigy"), EffigyComponent.class);

	@Override
	public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
		registry.beginRegistration(PlayerEntity.class, EFFIGY_COMPONENT).impl(EffigyComponent.class).end(EffigyComponent::new);
	}


}
