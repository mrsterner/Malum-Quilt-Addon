package dev.sterner.addon;

import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormats;
import dev.sterner.addon.client.AddonShaderRegistry;
import dev.sterner.addon.client.BeamBlockEntityRenderer;
import dev.sterner.addon.common.AddonBlockEntities;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.ShaderProgram;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

import static com.sammy.lodestone.systems.rendering.Phases.ADDITIVE_TRANSPARENCY;
import static net.minecraft.client.render.RenderPhase.*;

public class AddonClient implements ClientModInitializer {
/*
	public static final RenderLayer MAGIC = RenderLayer.of("magic", VertexFormats.POSITION_COLOR,
			VertexFormat.DrawMode.QUADS, 256, false, true, RenderLayer.MultiPhaseParameters.builder()
					.shader(RenderLayer.ENERGY_SWIRL_SHADER)//new RenderPhase.Shader(() -> AddonShaderRegistry.BEAM.getInstance().get()))
					.texture(new RenderPhase.Texture(new Identifier("addon", "textures/misc/beam.png"), false, false))
					.lightmap(ENABLE_LIGHTMAP)

					.texturing(new RenderPhase.OffsetTexturing(x, y))
					.transparency(ADDITIVE_TRANSPARENCY)
					.cull(DISABLE_CULLING)
					.overlay(ENABLE_OVERLAY_COLOR)
					.build(false));


 */

	public static RenderLayer getEnergySwirl(float x, float y) {
		return RenderLayer.of(
				"energy_swirl",
				VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL,
				VertexFormat.DrawMode.QUADS,
				256,
				false,
				true,
				RenderLayer.MultiPhaseParameters.builder()
						.shader(new RenderPhase.Shader(() -> AddonShaderRegistry.BEAM.getInstance().get()))
						.texture(new RenderPhase.Texture(new Identifier("addon", "textures/misc/beam.png"), false, false))
						.texturing(new RenderPhase.OffsetTexturing(x, y))
						.transparency(ADDITIVE_TRANSPARENCY)
						.cull(DISABLE_CULLING)
						.lightmap(ENABLE_LIGHTMAP)
						.overlay(ENABLE_OVERLAY_COLOR)
						.build(false)
		);
	}

	@Override
	public void onInitializeClient(ModContainer mod) {
		BlockEntityRendererFactories.register(AddonBlockEntities.BEAM, BeamBlockEntityRenderer::new);
	}


}
