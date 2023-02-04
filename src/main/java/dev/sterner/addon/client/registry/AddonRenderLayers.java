package dev.sterner.addon.client.registry;

import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormats;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.util.Identifier;

import static com.sammy.lodestone.systems.rendering.Phases.ADDITIVE_TRANSPARENCY;
import static net.minecraft.client.render.RenderPhase.*;

public class AddonRenderLayers {
	public static RenderLayer getEnergySwirl(float x, float y) {
		return RenderLayer.of(
				"energy_swirl",
				VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL,
				VertexFormat.DrawMode.QUADS,
				256,
				false,
				true,
				RenderLayer.MultiPhaseParameters.builder()
						.shader(new RenderPhase.Shader(() -> AddonShaders.BEAM.getInstance().get()))
						.texture(new RenderPhase.Texture(new Identifier("addon", "textures/misc/beam.png"), false, false))
						.texturing(new RenderPhase.OffsetTexturing(x, y))
						.transparency(ADDITIVE_TRANSPARENCY)
						.cull(DISABLE_CULLING)
						.lightmap(ENABLE_LIGHTMAP)
						.overlay(ENABLE_OVERLAY_COLOR)
						.build(false)
		);
	}
}
