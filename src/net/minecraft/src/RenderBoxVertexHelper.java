package net.minecraft.src;

import java.util.HashSet;
import java.util.Set;

public abstract class RenderBoxVertexHelper extends RenderVertexHelper {

	protected Set<Integer> noRenderSides = new HashSet<Integer>();
	protected IRenderVertexHelper topRender;
	protected IRenderVertexHelper bottomRender;
	protected IRenderVertexHelper northRender;
	protected IRenderVertexHelper southRender;
	protected IRenderVertexHelper eastRender;
	protected IRenderVertexHelper westRender;

	public RenderBoxVertexHelper(double fromX, double fromY, double fromZ,
			double toX, double toY, double toZ) {
		super(RenderQuadDrawHelper.HELPER, RenderNoColorHelper.HELPER, fromX,
				fromY, fromZ, toX, toY, toZ);
	}

	public RenderBoxVertexHelper(AxisAlignedBB box, double offsetX,
			double offsetY, double offsetZ) {
		super(RenderQuadDrawHelper.HELPER, RenderNoColorHelper.HELPER, box,
				offsetX, offsetY, offsetZ);
	}

	@Override
	public void drawVertex() {

		for (int i = 0; i < 6; i++) {
			if (noRenderSides.contains(i)) {
				continue;
			}
			switch (i) {
			case 0:
				topRender.drawVertex();

				break;
			case 1:
				bottomRender.drawVertex();

				break;
			case 2:
				northRender.drawVertex();

				break;
			case 3:
				southRender.drawVertex();

				break;
			case 4:
				eastRender.drawVertex();

				break;
			case 5:
				westRender.drawVertex();

				break;

			default:
				break;
			}
		}

	}

}