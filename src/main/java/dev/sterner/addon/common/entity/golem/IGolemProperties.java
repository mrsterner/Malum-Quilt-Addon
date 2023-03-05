package dev.sterner.addon.common.entity.golem;

import java.util.Set;

public interface IGolemProperties {
	Set<ArcaneGolemEntity.GolemTrait> getTraits();

	boolean hasTrait(ArcaneGolemEntity.GolemTrait trait);

	long toLong();

	void setMaterial(ArcaneGolemEntity.GolemMaterial var1);

	ArcaneGolemEntity.GolemMaterial getMaterial();
}
