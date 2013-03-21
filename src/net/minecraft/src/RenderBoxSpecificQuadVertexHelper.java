package net.minecraft.src;

public class RenderBoxSpecificQuadVertexHelper extends RenderBoxVertexHelper {

	public RenderBoxSpecificQuadVertexHelper(IRenderDrawModeHelper drawHelper,
			IRenderDrawColorHelper colorHelper, AxisAlignedBB box,
			double offsetX, double offsetY, double offsetZ) {
		super(box, offsetX, offsetY, offsetZ);

		initQuadVertex(drawHelper, colorHelper);
	}

	public RenderBoxSpecificQuadVertexHelper(IRenderDrawModeHelper drawHelper,
			IRenderDrawColorHelper colorHelper, double fromX, double fromY,
			double fromZ, double toX, double toY, double toZ) {
		super(fromX, fromY, fromZ, toX, toY, toZ);

		initQuadVertex(drawHelper, colorHelper);
	}

	protected void initQuadVertex(IRenderDrawModeHelper drawHelper,
			IRenderDrawColorHelper colorHelper) {
		topRender = getQuadVertexHelper(Direction.XZ, drawHelper, colorHelper,
				this.fromX, this.fromY, this.fromZ, this.toX, this.toY,
				this.toZ).setBlight(1.0F);
		bottomRender = getQuadVertexHelper(Direction.XZ, drawHelper,
				colorHelper, this.fromX, this.fromY, this.fromZ, this.toX,
				this.toY, this.toZ).setBlight(0.5F).setFlip();

		northRender = getQuadVertexHelper(Direction.XY, drawHelper,
				colorHelper, this.fromX, this.fromY, this.fromZ, this.toX,
				this.toY, this.fromZ).setBlight(0.6F);
		southRender = getQuadVertexHelper(Direction.XY, drawHelper,
				colorHelper, this.fromX, this.fromY, this.toZ, this.toX,
				this.toY, this.toZ).setBlight(0.6F).setFlip();

		eastRender = getQuadVertexHelper(Direction.YZ, drawHelper, colorHelper,
				this.fromX, this.fromY, this.fromZ, this.fromX, this.toY,
				this.toZ).setBlight(0.8F);
		westRender = getQuadVertexHelper(Direction.YZ, drawHelper, colorHelper,
				this.toX, this.fromY, this.fromZ, this.toX, this.toY, this.toZ)
				.setBlight(0.8F).setFlip();
	}

	protected enum Direction {
		XY, XZ, YZ,
	}

	protected RenderQuadVertexHelper getQuadVertexHelper(Direction dir,
			IRenderDrawModeHelper drawHelper,
			IRenderDrawColorHelper colorHelper, double fromX, double fromY,
			double fromZ, double toX, double toY, double toZ) {
		switch (dir) {
		case XY:
			return new RenderXYQuadVertexHelper(drawHelper, colorHelper, fromX,
					fromY, fromZ, toX, toY, toZ);

		case XZ:
			return new RenderXZQuadVertexHelper(drawHelper, colorHelper, fromX,
					fromY, fromZ, toX, toY, toZ);

		case YZ:
			return new RenderYZQuadVertexHelper(drawHelper, colorHelper, fromX,
					fromY, fromZ, toX, toY, toZ);

		default:
			break;
		}
		return null;
	}

}
