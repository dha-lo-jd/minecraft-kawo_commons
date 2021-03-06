package net.minecraft.src;




public class RenderXZQuadVertexHelper extends RenderQuadVertexHelper {

	public RenderXZQuadVertexHelper(IRenderDrawModeHelper drawHelper,
			IRenderDrawColorHelper colorHelper, AxisAlignedBB box,
			double offsetX, double offsetY, double offsetZ) {
		super(drawHelper, colorHelper, box, offsetX, offsetY, offsetZ);
	}

	public RenderXZQuadVertexHelper(IRenderDrawModeHelper drawHelper,
			IRenderDrawColorHelper colorHelper, double fromX, double fromY,
			double fromZ, double toX, double toY, double toZ) {
		super(drawHelper, colorHelper, fromX, fromY, fromZ, toX, toY, toZ);
	}

	@Override
	protected void doDrawVertex(double fromX, double fromY, double fromZ,
			double toX, double toY, double toZ) {
		Tessellator tessellator = Tessellator.instance;
		if (flip) {
			tessellator.setNormal(0, -1, 0);
			if (face == RenderFace.NORMAL || face == RenderFace.BOTH) {
				tessellator.addVertex(fromX, fromY, toZ);
				tessellator.addVertex(fromX, fromY, fromZ);
				tessellator.addVertex(toX, fromY, fromZ);
				tessellator.addVertex(toX, fromY, toZ);
			}
			if (face == RenderFace.REVERSE || face == RenderFace.BOTH) {
				tessellator.addVertex(toX, fromY, toZ);
				tessellator.addVertex(toX, fromY, fromZ);
				tessellator.addVertex(fromX, fromY, fromZ);
				tessellator.addVertex(fromX, fromY, toZ);
			}
		} else {
			tessellator.setNormal(0, 1, 0);
			if (face == RenderFace.NORMAL || face == RenderFace.BOTH) {
				tessellator.addVertex(toX, toY, toZ);
				tessellator.addVertex(toX, toY, fromZ);
				tessellator.addVertex(fromX, toY, fromZ);
				tessellator.addVertex(fromX, toY, toZ);
			}
			if (face == RenderFace.REVERSE || face == RenderFace.BOTH) {
				tessellator.addVertex(fromX, toY, toZ);
				tessellator.addVertex(fromX, toY, fromZ);
				tessellator.addVertex(toX, toY, fromZ);
				tessellator.addVertex(toX, toY, toZ);
			}
		}
	}

}
