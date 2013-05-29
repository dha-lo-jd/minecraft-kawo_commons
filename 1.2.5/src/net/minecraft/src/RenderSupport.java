package net.minecraft.src;




public class RenderSupport {

	public static float GLOBAL_ALPHA = 1;

	public static void drawBoxQuads(AxisAlignedBB selectedBoundingBoxFromPool,
			double posX, double posY, double posZ, int color) {
		RenderPaintColorHelper paintColorHelper = new RenderPaintColorHelper(
				color);

		RenderBoxVertexHelper vertexHelper = new RenderBoxSpecificQuadVertexHelper(
				RenderQuadDrawHelper.HELPER, paintColorHelper,
				selectedBoundingBoxFromPool, posX, posY, posZ);
		vertexHelper.drawVertex();
	}

	public static void drawBox(AxisAlignedBB selectedBoundingBoxFromPool,
			double posX, double posY, double posZ, int color) {
		RenderPaintColorHelper paintColorHelper = new RenderPaintColorHelper(
				color);
		RenderLineDrawHelper lineDrawHelper = RenderLineDrawHelper.HELPER;

		RenderBoxVertexHelper vertexHelper = new RenderBoxSpecificQuadVertexHelper(
				lineDrawHelper, paintColorHelper, selectedBoundingBoxFromPool,
				posX, posY, posZ);
		vertexHelper.drawVertex();
	}

}
