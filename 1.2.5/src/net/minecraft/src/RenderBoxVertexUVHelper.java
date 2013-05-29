package net.minecraft.src;




public class RenderBoxVertexUVHelper extends RenderBoxSpecificQuadVertexHelper {

	public RenderBoxVertexUVHelper(IRenderDrawModeHelper drawHelper,
			IRenderDrawColorHelper colorHelper, AxisAlignedBB box,
			double offsetX, double offsetY, double offsetZ, double fromU,
			double fromV, double toU, double toV) {
		super(drawHelper, colorHelper, box, offsetX, offsetY, offsetZ);
		this.fromU = fromU;
		this.fromV = fromV;
		this.toU = toU;
		this.toV = toV;

		initQuadVertexUV(drawHelper, colorHelper);
	}

	public RenderBoxVertexUVHelper(IRenderDrawModeHelper drawHelper,
			IRenderDrawColorHelper colorHelper, double fromX, double fromY,
			double fromZ, double toX, double toY, double toZ, double fromU,
			double fromV, double toU, double toV) {
		super(drawHelper, colorHelper, fromX, fromY, fromZ, toX, toY, toZ);
		this.fromU = fromU;
		this.fromV = fromV;
		this.toU = toU;
		this.toV = toV;

		initQuadVertexUV(drawHelper, colorHelper);
	}

	double fromU;
	double fromV;

	double toU;
	double toV;

	@Override
	protected RenderQuadVertexHelper getQuadVertexHelper(Direction dir,
			IRenderDrawModeHelper drawHelper,
			IRenderDrawColorHelper colorHelper, double fromX, double fromY,
			double fromZ, double toX, double toY, double toZ) {
		switch (dir) {
		case XY:
			return new RenderXYQuadVertexUVHelper(drawHelper, colorHelper,
					fromX, fromY, fromZ, toX, toY, toZ, fromU, fromV, toU, toV);

		case XZ:
			return new RenderXZQuadVertexUVHelper(drawHelper, colorHelper,
					fromX, fromY, fromZ, toX, toY, toZ, fromU, fromV, toU, toV);

		case YZ:
			return new RenderYZQuadVertexUVHelper(drawHelper, colorHelper,
					fromX, fromY, fromZ, toX, toY, toZ, fromU, fromV, toU, toV);

		default:
			break;
		}
		return null;
	}

	@Override
	protected void initQuadVertex(IRenderDrawModeHelper drawHelper,
			IRenderDrawColorHelper colorHelper) {
	}

	protected void initQuadVertexUV(IRenderDrawModeHelper drawHelper,
			IRenderDrawColorHelper colorHelper) {
		super.initQuadVertex(drawHelper, colorHelper);
	}

}
