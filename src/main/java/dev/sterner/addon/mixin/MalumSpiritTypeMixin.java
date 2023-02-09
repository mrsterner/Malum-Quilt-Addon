package dev.sterner.addon.mixin;

import dev.sterner.addon.common.registry.AddonObjects;
import dev.sterner.malum.common.registry.MalumObjects;
import dev.sterner.malum.common.spirit.MalumSpiritType;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;
import java.util.function.Supplier;

@Mixin(value = MalumSpiritType.class, remap = false)
public class MalumSpiritTypeMixin {

	@Shadow
	public Supplier<Item> splinterItem;

	@Inject(method = "<init>(Ljava/lang/String;Ljava/awt/Color;Ljava/util/function/Supplier;)V", at = @At("TAIL"))
	private void addon$injectType(String identifier, Color color, Supplier splinterItem, CallbackInfo ci){
		if (identifier.equals("damned")) {
			this.splinterItem = () -> AddonObjects.DAMNED_SPIRIT;
		}
	}
}
