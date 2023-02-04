package dev.sterner.addon;

import com.sammy.lodestone.systems.blockentity.LodestoneBlockEntityInventory;
import com.sammy.lodestone.systems.recipe.IngredientWithCount;
import dev.sterner.addon.client.models.equipment.HallowedGogglesModel;
import dev.sterner.addon.client.renderer.BeamBlockEntityRenderer;
import dev.sterner.addon.client.renderer.equipment.armor.HallowedGogglesRenderer;
import dev.sterner.addon.common.registry.AddonBlockEntities;
import dev.sterner.addon.common.registry.AddonObjects;
import dev.sterner.addon.common.util.RenderUtils;
import dev.sterner.malum.Malum;
import dev.sterner.malum.common.block.spirit_altar.SpiritAltarBlock;
import dev.sterner.malum.common.blockentity.spirit_altar.SpiritAltarBlockEntity;
import dev.sterner.malum.common.recipe.SpiritInfusionRecipe;
import dev.sterner.malum.common.recipe.SpiritTransmutationRecipe;
import dev.sterner.malum.common.recipe.SpiritWithCount;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
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
		ArmorRenderer.register(new HallowedGogglesRenderer(), AddonObjects.HALLOWED_GOGGLES);


		HudRenderCallback.EVENT.register((matrixStack, tickDelta) -> {
			MinecraftClient client = MinecraftClient.getInstance();
			if(client.player != null && client.player.getEquippedStack(EquipmentSlot.HEAD).isOf(AddonObjects.HALLOWED_GOGGLES)){
				ItemRenderer itemRenderer = client.getItemRenderer();
				if(client.world != null && client.crosshairTarget instanceof BlockHitResult result){
					BlockPos pos = result.getBlockPos();
					if(client.world.getBlockEntity(pos) instanceof SpiritAltarBlockEntity se){
						int startX = client.getWindow().getScaledWidth() / 2;
						int centerY = client.getWindow().getScaledHeight() / 2;
						matrixStack.push();
						matrixStack.translate(client.getWindow().getScaledWidth() % 2 != 0 ? 0.5 : 0, client.getWindow().getScaledHeight() % 2 != 0 ? 0.5 : 0, 0);
						List<SpiritInfusionRecipe> recipes = se.possibleRecipes;
						if(!recipes.isEmpty()){
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
						}
						matrixStack.pop();
					}
				}
			}
		});
	}
}
