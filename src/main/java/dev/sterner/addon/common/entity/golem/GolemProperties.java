package dev.sterner.addon.common.entity.golem;

import dev.sterner.addon.common.util.AddonUtils;

import java.util.Set;

public class GolemProperties implements IGolemProperties{
	private long data = 0L;
	private Set<ArcaneGolemEntity.GolemTrait> traitCache = null;

	@Override
	public Set<ArcaneGolemEntity.GolemTrait> getTraits() {
		return null;
	}

	@Override
	public boolean hasTrait(ArcaneGolemEntity.GolemTrait trait) {
		return false;
	}

	@Override
	public long toLong() {
		return this.data;
	}

	public static IGolemProperties fromLong(long d) {
		GolemProperties out = new GolemProperties();
		out.data = d;
		return out;
	}

	@Override
	public void setMaterial(ArcaneGolemEntity.GolemMaterial material) {
		this.data = AddonUtils.setByteInLong(this.data, material.id, 0);
		this.traitCache = null;
	}

	@Override
	public ArcaneGolemEntity.GolemMaterial getMaterial() {
		return ArcaneGolemEntity.GolemMaterial.getMaterials()[AddonUtils.getByteInLong(this.data, 0)];
	}
}
