package dev.sterner.addon;

import com.chocohead.mm.api.ClassTinkerers;
import com.llamalad7.mixinextras.MixinExtrasBootstrap;
import com.mojang.logging.LogUtils;
import dev.sterner.malum.common.registry.MalumObjects;
import dev.sterner.malum.common.util.ModdedErrStream;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvents;
import org.quiltmc.loader.api.MappingResolver;
import org.quiltmc.loader.api.QuiltLoader;

import java.util.function.Supplier;

public final class AddonEarlyRiser implements Runnable {
	@SuppressWarnings("rawtypes")
	@Override
	public void run() {
		MixinExtrasBootstrap.init();

		final MappingResolver mappings = QuiltLoader.getMappingResolver();
		final String armorMaterialsTarget = mappings.mapClassName("intermediary", "net.minecraft.class_1740");
		final String armorParam5 = "L" + mappings.mapClassName("intermediary", "net.minecraft.class_3414") + ";";




	}

	static {
		System.setErr(new ModdedErrStream("STDERR", System.err, LogUtils.getLogger().isDebugEnabled()));
	}
}
