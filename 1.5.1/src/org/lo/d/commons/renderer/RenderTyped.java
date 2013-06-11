package org.lo.d.commons.renderer;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;

public abstract class RenderTyped<E extends Entity> extends Render {

	@Override
	public final void doRender(Entity par1Entity, double par2, double par4,
			double par6, float par8, float par9) {
		@SuppressWarnings("unchecked")
		E typedEntity = (E) par1Entity;
		doRenderTyped(typedEntity, par2, par4, par6, par8, par9);
	}

	/**
	 * The actual render method that is used in doRender
	 */
	public abstract void doRenderTyped(E entity, double par2, double par4,
			double par6, float par8, float par9);
}
