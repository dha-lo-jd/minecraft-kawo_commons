package net.minecraft.src;

public class RenderNoDrawHelper implements IRenderDrawModeHelper {

	public static final RenderNoDrawHelper HELPER = new RenderNoDrawHelper();

	private RenderNoDrawHelper() {
	}

	@Override
	public void startDrawing() {
	}

	@Override
	public void finishDraw() {
	}

	@Override
	public boolean isLine() {
		return false;
	}

}
