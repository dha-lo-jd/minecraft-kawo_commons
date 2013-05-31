package org.lo.d.commons.coords;

import net.minecraft.entity.Entity;

public class EntityPoint3DDouble extends Point3DDouble {
	private final Entity entity;

	public EntityPoint3DDouble(Entity entity) {
		super(entity.posX, entity.posY, entity.posZ);
		this.entity = entity;
	}

	public Entity getEntity() {
		return entity;
	}
}