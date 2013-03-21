package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class RenderLineDrawHelper implements IRenderDrawModeHelper {

	public static final RenderLineDrawHelper HELPER = new RenderLineDrawHelper();

	private RenderLineDrawHelper() {
	}

	// protected void putVertex(int color, double fromX, double fromY,
	// double fromZ, double toX, double toY, double toZ) {
	// Tessellator tessellator = Tessellator.instance;
	// tessellator.startDrawing(3);
	// tessellator.setColorOpaque_I(color);
	// tessellator.addVertex(fromX, fromY, fromZ);
	// tessellator.addVertex(toX, toY, toZ);
	// tessellator.draw();
	// }

	@Override
	public void startDrawing() {
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawing(GL11.GL_LINE_LOOP);
	}

	@Override
	public void finishDraw() {
		Tessellator tessellator = Tessellator.instance;
		tessellator.draw();
	}

	@Override
	public boolean isLine() {
		return true;
	}

}
