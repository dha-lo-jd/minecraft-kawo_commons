package net.minecraft.src;

public class RenderNoVertexHelper extends RenderVertexHelper {

	public static final RenderNoVertexHelper HELPER = new RenderNoVertexHelper();

	private RenderNoVertexHelper() {
		super(null, null, 0, 0, 0, 0, 0, 0);
	}

	@Override
	public void drawVertex() {
	}

}
