package org.lo.d.commons.renderer;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderIcon extends Render {

	public String texture;

	public RenderIcon(String texture) {
		shadowSize = 0.15F;
		shadowOpaque = 0.75F;
		this.texture = texture;
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method,
	 * always casting down its argument and then handing it off to a worker
	 * function which does the actual work. In all probabilty, the class Render
	 * is generic (Render<T extends Entity) and this method has signature public
	 * void doRender(T entity, double d, double d1, double d2, float f, float
	 * f1). But JAD is pre 1.5 so doesn't do that.
	 */
	@Override
	public void doRender(Entity par1Entity, double par2, double par4,
			double par6, float par8, float par9) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) par2, (float) par4, (float) par6);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glRotatef(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		float f = 0F;
		float f1 = 16 / 256F;
		float f4 = 1.0F;
		float f5 = 0.5F;
		float f6 = 0.25F;

		loadTexture(texture);

		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		tessellator.addVertexWithUV(0.0F - f5, 0.0F - f6, 0.0D, f, f1);
		tessellator.addVertexWithUV(f4 - f5, 0.0F - f6, 0.0D, f, f1);
		tessellator.addVertexWithUV(f4 - f5, 1.0F - f6, 0.0D, f, f1);
		tessellator.addVertexWithUV(0.0F - f5, 1.0F - f6, 0.0D, f, f1);
		tessellator.draw();
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
	}
}
