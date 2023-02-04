package dev.sterner.addon;

import com.sammy.lodestone.systems.recipe.IngredientWithCount;
import dev.sterner.addon.client.models.entity.EffigyEntityModel;
import dev.sterner.addon.client.models.equipment.armor.HallowedGogglesModel;
import dev.sterner.addon.client.renderer.BeamBlockEntityRenderer;
import dev.sterner.addon.client.renderer.entity.EffigyEntityRenderer;
import dev.sterner.addon.client.renderer.equipment.armor.HallowedGogglesRenderer;
import dev.sterner.addon.common.registry.AddonBlockEntities;
import dev.sterner.addon.common.registry.AddonEntityTypes;
import dev.sterner.addon.common.registry.AddonObjects;
import dev.sterner.addon.common.util.RenderUtils;
import dev.sterner.malum.common.blockentity.spirit_altar.SpiritAltarBlockEntity;
import dev.sterner.malum.common.recipe.SpiritInfusionRecipe;
import dev.sterner.malum.common.recipe.SpiritWithCount;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

import java.util.List;


public class AddonClient implements ClientModInitializer {

	@Override
	public void onInitializeClient(ModContainer mod) {
		BlockEntityRendererFactories.register(AddonBlockEntities.BEAM, BeamBlockEntityRenderer::new);

		EntityModelLayerRegistry.registerModelLayer(HallowedGogglesModel.LAYER, HallowedGogglesModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(EffigyEntityModel.LAYER, EffigyEntityModel::getTexturedModelData);

		EntityRendererRegistry.register(AddonEntityTypes.EFFIGY, EffigyEntityRenderer::new);

		ArmorRenderer.register(new HallowedGogglesRenderer(), AddonObjects.HALLOWED_GOGGLES);


		HudRenderCallback.EVENT.register((matrixStack, tickDelta) -> {
			MinecraftClient client = MinecraftClient.getInstance();
			if(client.player != null && client.player.getEquippedStack(EquipmentSlot.HEAD).isOf(AddonObjects.HALLOWED_GOGGLES)){
				if(client.world != null && client.crosshairTarget instanceof BlockHitResult result){
					BlockPos pos = result.getBlockPos();
					if(client.world.getBlockEntity(pos) instanceof SpiritAltarBlockEntity se){
						List<SpiritInfusionRecipe> recipes = se.possibleRecipes;
						if(!recipes.isEmpty()){
							matrixStack.push();
							matrixStack.translate(client.getWindow().getScaledWidth() % 2 != 0 ? 0.5 : 0, client.getWindow().getScaledHeight() % 2 != 0 ? 0.5 : 0, 0);
							int startX = client.getWindow().getScaledWidth() / 2;
							int centerY = client.getWindow().getScaledHeight() / 2;
							ItemRenderer itemRenderer = client.getItemRenderer();
							startX -= recipes.size() / 2 * 16;
							centerY += 10;
							matrixStack.translate(startX, centerY, 0);
							SpiritInfusionRecipe recipe = recipes.get(0);
							List<SpiritWithCount> spirits = recipe.spirits;
							List<IngredientWithCount> extras = recipe.extraItems;
							ItemStack inventory = se.extrasInventory.getStack(0);
							for(IngredientWithCount extra : extras){
								ItemStack renderStack = new ItemStack(extra.getItem(), extra.getCount());
								boolean checked = extra.getItem() == inventory.getItem();
								itemRenderer.renderInGuiWithOverrides(renderStack, startX, centerY);
								if(!checked){
									itemRenderer.renderGuiItemOverlay(client.textRenderer, renderStack, startX, centerY);
								}else{
									RenderUtils.drawIcon(matrixStack, RenderUtils.CHECKMARK);
								}
								matrixStack.translate(16, 0, 0);
							}
							matrixStack.pop();
						}
					}
				}
			}
		});
	}
}
