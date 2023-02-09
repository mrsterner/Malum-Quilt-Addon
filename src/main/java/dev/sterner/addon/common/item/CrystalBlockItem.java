package dev.sterner.addon.common.item;

import com.sammy.lodestone.handlers.screenparticle.ParticleEmitterHandler;
import com.sammy.lodestone.systems.particle.screen.LodestoneScreenParticleTextureSheet;
import com.sammy.lodestone.systems.particle.screen.base.ScreenParticle;
import dev.sterner.addon.common.registry.AddonSpiritTypes;
import dev.sterner.malum.client.CommonParticleEffects;
import dev.sterner.malum.common.spirit.MalumSpiritType;
import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class CrystalBlockItem extends BlockItem {
	public MalumSpiritType type;
	public CrystalBlockItem(Block block, Settings settings, MalumSpiritType type) {
		super(block, settings);
		this.type = type;
	}
}
