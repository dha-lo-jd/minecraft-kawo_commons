package net.minecraft.src;

public abstract class RenderQuadVertexUVHelper extends RenderQuadVertexHelper {

	public RenderQuadVertexUVHelper(IRenderDrawModeHelper drawHelper,
			IRenderDrawColorHelper colorHelper, AxisAlignedBB box,
			double offsetX, double offsetY, double offsetZ, double fromU,
			double fromV, double toU, double toV) {
		super(drawHelper, colorHelper, box, offsetX, offsetY, offsetZ);
		this.fromU = fromU;
		this.fromV = fromV;
		this.toU = toU;
		this.toV = toV;
	}

	public RenderQuadVertexUVHelper(IRenderDrawModeHelper drawHelper,
			IRenderDrawColorHelper colorHelper, double fromX, double fromY,
			double fromZ, double toX, double toY, double toZ, double fromU,
			double fromV, double toU, double toV) {
		super(drawHelper, colorHelper, fromX, fromY, fromZ, toX, toY, toZ);
		this.fromU = fromU;
		this.fromV = fromV;
		this.toU = toU;
		this.toV = toV;
	}

	double fromU;
	double fromV;

	double toU;
	double toV;

	public RenderQuadVertexUVHelper setUV(double fromU, double fromV,
			double toU, double toV) {
		this.fromU = fromU;
		this.fromV = fromV;
		this.toU = toU;
		this.toV = toV;
		return this;
	}

}