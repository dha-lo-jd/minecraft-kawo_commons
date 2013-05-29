package net.minecraft.src;




public class RenderLineVertexHelper extends RenderVertexHelper {

	public RenderLineVertexHelper(IRenderDrawModeHelper drawHelper,
			IRenderDrawColorHelper colorHelper, AxisAlignedBB box,
			double offsetX, double offsetY, double offsetZ) {
		super(drawHelper, colorHelper, box, offsetX, offsetY, offsetZ);
	}

	public RenderLineVertexHelper(IRenderDrawModeHelper drawHelper,
			IRenderDrawColorHelper colorHelper, double fromX, double fromY,
			double fromZ, double toX, double toY, double toZ) {
		super(drawHelper, colorHelper, fromX, fromY, fromZ, toX, toY, toZ);
	}

	@Override
	public void drawVertex() {
		Tessellator tessellator = Tessellator.instance;
		drawHelper.startDrawing();
		colorHelper.setColor(1);
		tessellator.addVertex(fromX, fromY, fromZ);
		tessellator.addVertex(toX, toY, toZ);
		drawHelper.finishDraw();
	}

}
