package dev.sterner.addon.mixin;

import dev.sterner.addon.common.registry.AddonObjects;
import dev.sterner.malum.common.item.spirit.MalumSpiritItem;
import dev.sterner.malum.common.spirit.MalumSpiritType;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.awt.*;
import java.util.Map;
import java.util.function.Supplier;

@Mixin(value = MalumSpiritType.class, remap = false)
public class MalumSpiritTypeMixin {

	@Shadow
	public Map<Supplier<MalumSpiritItem>, String> SPIRITS;

	@Inject(method = "<init>(Ljava/lang/String;Ljava/awt/Color;Ljava/awt/Color;)V", at = @At(value = "INVOKE", target = "Ljava/util/Map;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", ordinal = 1))
	private void addon$injectType(String identifier, Color color, Color endColor, CallbackInfo ci){
		SPIRITS.put(() -> AddonObjects.DAMNED_SPIRIT, "damned");
	}
}
