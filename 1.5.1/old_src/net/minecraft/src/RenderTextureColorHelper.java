package net.minecraft.src;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;

import org.lwjgl.opengl.GL11;

public class RenderTextureColorHelper implements IRenderDrawColorHelper {

	int color;
	Render render;
	String texture;

	public RenderTextureColorHelper(int color, Render render, String texture) {
		super();
		this.color = color;
		this.render = render;
		this.texture = texture;
	}

	@Override
	public void setColor(float blight) {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		Tessellator tessellator = Tessellator.instance;
		tessellator.setBrightness(15728640);
		float r = (float) (color >> 16 & 0xff) / 255F;
		float g = (float) (color >> 8 & 0xff) / 255F;
		float b = (float) (color & 0xff) / 255F;
		tessellator.setColorRGBA_F(r * blight, g * blight, b * blight,
				RenderSupport.GLOBAL_ALPHA);
		render.loadTexture(texture);
	}

}
