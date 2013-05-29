package net.minecraft.src;

import net.minecraft.client.renderer.Tessellator;

import org.lwjgl.opengl.GL11;

public class RenderPaintColorHelper implements IRenderDrawColorHelper {

	int color;

	public RenderPaintColorHelper(int color) {
		this.color = color;
	}

	@Override
	public void setColor(float blight) {
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		Tessellator tessellator = Tessellator.instance;
		tessellator.setBrightness(15728640);
		float r = (float) (color >> 16 & 0xff) / 255F;
		float g = (float) (color >> 8 & 0xff) / 255F;
		float b = (float) (color & 0xff) / 255F;
		// tessellator.setColorOpaque_F(r * blight, g * blight, b * blight);
		tessellator.setColorRGBA_F(r * blight, g * blight, b * blight,
				RenderSupport.GLOBAL_ALPHA);
	}

}
