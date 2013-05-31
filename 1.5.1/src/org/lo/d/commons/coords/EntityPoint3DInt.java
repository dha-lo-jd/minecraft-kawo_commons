package org.lo.d.commons.coords;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class EntityPoint3DInt extends Point3D {
	private final Entity entity;

	public EntityPoint3DInt(Entity entity) {
		super(MathHelper.floor_double(entity.posX), MathHelper.floor_double(entity.posY), MathHelper
				.floor_double(entity.posZ));
		this.entity = entity;
	}

	public Entity getEntity() {
		return entity;
	}
}