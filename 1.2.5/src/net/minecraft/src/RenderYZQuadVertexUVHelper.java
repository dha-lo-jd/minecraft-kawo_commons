package net.minecraft.src;




public class RenderYZQuadVertexUVHelper extends RenderQuadVertexUVHelper {

	public RenderYZQuadVertexUVHelper(IRenderDrawModeHelper drawHelper,
			IRenderDrawColorHelper colorHelper, AxisAlignedBB box,
			double offsetX, double offsetY, double offsetZ, double fromU,
			double fromV, double toU, double toV) {
		super(drawHelper, colorHelper, box, offsetX, offsetY, offsetZ, fromU,
				fromV, toU, toV);
	}

	public RenderYZQuadVertexUVHelper(IRenderDrawModeHelper drawHelper,
			IRenderDrawColorHelper colorHelper, double fromX, double fromY,
			double fromZ, double toX, double toY, double toZ, double fromU,
			double fromV, double toU, double toV) {
		super(drawHelper, colorHelper, fromX, fromY, fromZ, toX, toY, toZ,
				fromU, fromV, toU, toV);
	}

	@Override
	protected void doDrawVertex(double fromX, double fromY, double fromZ,
			double toX, double toY, double toZ) {
		Tessellator tessellator = Tessellator.instance;
		if (flip) {
			tessellator.setNormal(1, 0, 0);
			if (face == RenderFace.NORMAL || face == RenderFace.BOTH) {
				tessellator.addVertexWithUV(toX, fromY, fromZ, fromU, fromV);
				tessellator.addVertexWithUV(toX, toY, fromZ, fromU, toV);
				tessellator.addVertexWithUV(toX, toY, toZ, toU, toV);
				tessellator.addVertexWithUV(toX, fromY, toZ, toU, fromV);
			}
			if (face == RenderFace.REVERSE || face == RenderFace.BOTH) {
				tessellator.addVertexWithUV(toX, fromY, toZ, toU, fromV);
				tessellator.addVertexWithUV(toX, toY, toZ, toU, toV);
				tessellator.addVertexWithUV(toX, toY, fromZ, fromU, toV);
				tessellator.addVertexWithUV(toX, fromY, fromZ, fromU, fromV);
			}
		} else {
			tessellator.setNormal(-1, 0, 0);
			if (face == RenderFace.NORMAL || face == RenderFace.BOTH) {
				tessellator.addVertexWithUV(fromX, fromY, fromZ, fromU, fromV);
				tessellator.addVertexWithUV(fromX, fromY, toZ, toU, fromV);
				tessellator.addVertexWithUV(fromX, toY, toZ, toU, toV);
				tessellator.addVertexWithUV(fromX, toY, fromZ, fromU, toV);
			}
			if (face == RenderFace.REVERSE || face == RenderFace.BOTH) {
				tessellator.addVertexWithUV(fromX, toY, fromZ, fromU, toV);
				tessellator.addVertexWithUV(fromX, toY, toZ, toU, toV);
				tessellator.addVertexWithUV(fromX, fromY, toZ, toU, fromV);
				tessellator.addVertexWithUV(fromX, fromY, fromZ, fromU, fromV);
			}
		}
	}

}
