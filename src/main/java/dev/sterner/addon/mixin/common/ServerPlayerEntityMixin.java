package dev.sterner.addon.mixin.common;

import com.mojang.authlib.GameProfile;
import dev.sterner.addon.common.component.AddonComponents;
import dev.sterner.addon.common.entity.EffigyEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {
	@Shadow
	public abstract ServerWorld getWorld();

	public ServerPlayerEntityMixin(World world, BlockPos pos, float f, GameProfile gameProfile) {
		super(world, pos, f, gameProfile);
	}


	@Inject(method = "onDeath", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;dropShoulderEntities()V"), cancellable = true)
	private void effigySavesTheDay(CallbackInfo ci) {
		if (!this.getWorld().isClient() && AddonComponents.EFFIGY_COMPONENT.maybeGet(this).isPresent()) {
			ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
			AddonComponents.EFFIGY_COMPONENT.maybeGet(player).ifPresent(effigyComponent -> {
				Entity entity = ((ServerWorld) player.world).getEntity(effigyComponent.getEffigy());
				if (entity instanceof EffigyEntity effigyEntity) {
					player.teleport(effigyEntity.getX(),effigyEntity.getY(),effigyEntity.getZ());
					player.setHealth(effigyEntity.getHealth());
					if(effigyEntity.isOnFire()){
						player.setOnFireFor(effigyEntity.getFireTicks());
					}
					effigyEntity.playSound(SoundEvents.ITEM_TOTEM_USE, 1F,1F);
					effigyEntity.teleport(0,-256,0);
					effigyEntity.kill();
					ServerWorld serverWorld = (ServerWorld) effigyEntity.world;
					serverWorld.setChunkForced(effigyEntity.getChunkPos().x,effigyEntity.getChunkPos().z,false);
					effigyComponent.setHasEffigy(false);
					effigyComponent.setEffigy(null);
					ci.cancel();
				}
			});
		}
	}
}
