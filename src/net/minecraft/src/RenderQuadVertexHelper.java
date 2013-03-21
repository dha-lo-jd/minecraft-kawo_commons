package net.minecraft.src;

public abstract class RenderQuadVertexHelper extends RenderVertexHelper {
	public enum RenderFace {
		NOTHING, NORMAL, REVERSE, BOTH,
	}

	boolean flip;
	float blight = 1.0F;
	RenderFace face = RenderFace.NORMAL;

	public void setFace(RenderFace face) {
		this.face = face;
	}

	public RenderQuadVertexHelper(IRenderDrawModeHelper drawHelper,
			IRenderDrawColorHelper colorHelper, AxisAlignedBB box,
			double offsetX, double offsetY, double offsetZ) {
		super(drawHelper, colorHelper, box, offsetX, offsetY, offsetZ);
	}

	public RenderQuadVertexHelper(IRenderDrawModeHelper drawHelper,
			IRenderDrawColorHelper colorHelper, double fromX, double fromY,
			double fromZ, double toX, double toY, double toZ) {
		super(drawHelper, colorHelper, fromX, fromY, fromZ, toX, toY, toZ);
	}

	abstract void doDrawVertex(double fromX, double fromY, double fromZ,
			double toX, double toY, double toZ);

	@Override
	public void drawVertex() {
		Tessellator tessellator = Tessellator.instance;
		drawHelper.startDrawing();
		colorHelper.setColor(blight);
		doDrawVertex(fromX, fromY, fromZ, toX, toY, toZ);
		drawHelper.finishDraw();
	}

	public RenderQuadVertexHelper setFlip() {
		this.flip = true;
		return this;
	}

	public RenderQuadVertexHelper setBlight(float blight) {
		this.blight = blight;
		return this;
	}

}