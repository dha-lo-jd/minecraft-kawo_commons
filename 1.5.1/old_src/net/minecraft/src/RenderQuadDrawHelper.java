package net.minecraft.src;

import net.minecraft.client.renderer.Tessellator;



public class RenderQuadDrawHelper implements IRenderDrawModeHelper {

	public static final RenderQuadDrawHelper HELPER = new RenderQuadDrawHelper();

	private RenderQuadDrawHelper() {
	}

	@Override
	public void startDrawing() {
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
	}

	@Override
	public void finishDraw() {
		Tessellator tessellator = Tessellator.instance;
		tessellator.draw();
	}

	@Override
	public boolean isLine() {
		return false;
	}

}
