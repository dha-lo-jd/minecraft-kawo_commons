package net.minecraft.src;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.AxisAlignedBB;



public class RenderXZQuadVertexUVHelper extends RenderQuadVertexUVHelper {

	public RenderXZQuadVertexUVHelper(IRenderDrawModeHelper drawHelper,
			IRenderDrawColorHelper colorHelper, AxisAlignedBB box,
			double offsetX, double offsetY, double offsetZ, double fromU,
			double fromV, double toU, double toV) {
		super(drawHelper, colorHelper, box, offsetX, offsetY, offsetZ, fromU,
				fromV, toU, toV);
	}

	public RenderXZQuadVertexUVHelper(IRenderDrawModeHelper drawHelper,
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
			tessellator.setNormal(0, -1, 0);
			if (face == RenderFace.NORMAL || face == RenderFace.BOTH) {
				tessellator.addVertexWithUV(fromX, fromY, toZ, fromU, fromV);
				tessellator.addVertexWithUV(fromX, fromY, fromZ, fromU, toV);
				tessellator.addVertexWithUV(toX, fromY, fromZ, toU, toV);
				tessellator.addVertexWithUV(toX, fromY, toZ, toU, fromV);
			}
			if (face == RenderFace.REVERSE || face == RenderFace.BOTH) {
				tessellator.addVertexWithUV(fromX, fromY, fromZ, fromU, fromV);
				tessellator.addVertexWithUV(fromX, fromY, toZ, fromU, toV);
				tessellator.addVertexWithUV(toX, fromY, toZ, toU, toV);
				tessellator.addVertexWithUV(toX, fromY, fromZ, toU, fromV);
			}
		} else {
			tessellator.setNormal(0, 1, 0);
			if (face == RenderFace.NORMAL || face == RenderFace.BOTH) {
				tessellator.addVertexWithUV(fromX, toY, fromZ, fromU, fromV);
				tessellator.addVertexWithUV(fromX, toY, toZ, fromU, toV);
				tessellator.addVertexWithUV(toX, toY, toZ, toU, toV);
				tessellator.addVertexWithUV(toX, toY, fromZ, toU, fromV);
			}
			if (face == RenderFace.REVERSE || face == RenderFace.BOTH) {
				tessellator.addVertexWithUV(toX, toY, fromZ, fromU, fromV);
				tessellator.addVertexWithUV(toX, toY, toZ, fromU, toV);
				tessellator.addVertexWithUV(fromX, toY, toZ, toU, toV);
				tessellator.addVertexWithUV(fromX, toY, fromZ, toU, fromV);
			}
		}
	}

}
