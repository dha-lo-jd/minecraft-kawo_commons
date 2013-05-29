package net.minecraft.src;




public abstract class RenderTyped<E extends Entity> extends Render {

	/**
	 * The actual render method that is used in doRender
	 */
	public abstract void doRenderTyped(E entity, double par2, double par4,
			double par6, float par8, float par9);

	@Override
	public final void doRender(Entity par1Entity, double par2, double par4,
			double par6, float par8, float par9) {
		doRenderTyped((E) par1Entity, par2, par4, par6, par8, par9);
	}
}
