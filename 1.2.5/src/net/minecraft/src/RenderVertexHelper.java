package net.minecraft.src;




public abstract class RenderVertexHelper implements IRenderVertexHelper {
	IRenderDrawModeHelper drawHelper;
	IRenderDrawColorHelper colorHelper;
	double fromX;
	double fromY;
	double fromZ;
	double toX;
	double toY;
	double toZ;

	public RenderVertexHelper(IRenderDrawModeHelper drawHelper,
			IRenderDrawColorHelper colorHelper, double fromX, double fromY,
			double fromZ, double toX, double toY, double toZ) {
		super();
		this.drawHelper = drawHelper;
		this.colorHelper = colorHelper;
		this.fromX = fromX;
		this.fromY = fromY;
		this.fromZ = fromZ;
		this.toX = toX;
		this.toY = toY;
		this.toZ = toZ;
	}

	public RenderVertexHelper(IRenderDrawModeHelper drawHelper,
			IRenderDrawColorHelper colorHelper, AxisAlignedBB box,
			double offsetX, double offsetY, double offsetZ) {

		this.fromX = box.minX - offsetX;
		this.fromY = box.minY - offsetY;
		this.fromZ = box.minZ - offsetZ;
		this.toX = box.maxX - offsetX;
		this.toY = box.maxY - offsetY;
		this.toZ = box.maxZ - offsetZ;

		this.drawHelper = drawHelper;
		this.colorHelper = colorHelper;
	}
}
