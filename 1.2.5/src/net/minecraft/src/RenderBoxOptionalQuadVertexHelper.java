package net.minecraft.src;




public class RenderBoxOptionalQuadVertexHelper extends RenderBoxVertexHelper {

	public RenderBoxOptionalQuadVertexHelper(
			RenderQuadVertexHelper[] quadRenders, AxisAlignedBB box,
			double offsetX, double offsetY, double offsetZ) {
		super(box, offsetX, offsetY, offsetZ);

		initVertexes(quadRenders);

	}

	public static RenderQuadVertexHelper[] createVertexHelpers(
			IRenderDrawModeHelper[] drawHelper,
			IRenderDrawColorHelper[] colorHelper, String[] textures) {
		RenderQuadVertexHelper[] result = new RenderQuadVertexHelper[6];

		for (int i = 0; i < 6; i++) {
			IRenderDrawModeHelper dh = RenderNoDrawHelper.HELPER;
			if (drawHelper != null && drawHelper.length > i) {
				dh = drawHelper[i];
			}
			IRenderDrawColorHelper ch = RenderNoColorHelper.HELPER;
			if (colorHelper != null && colorHelper.length > i) {
				ch = colorHelper[i];
			}
			String tx = null;
			if (textures != null && textures.length > i) {
				tx = textures[i];
			}

			RenderQuadVertexHelper vertexHelper = null;
			float blight;
			switch (i) {
			case 0:
			case 1:
				blight = 1.0F;
				if (i != 0) {
					blight = 0.5F;
				}
				if (tx != null) {
					vertexHelper = new RenderXZQuadVertexUVHelper(dh, ch, 0, 0,
							0, 0, 0, 0, 0, 0, 1, 1).setBlight(blight);
				} else {
					vertexHelper = new RenderXZQuadVertexHelper(dh, ch, 0, 0,
							0, 0, 0, 0).setBlight(blight);
				}

				break;
			case 2:
			case 3:
				blight = 0.6F;
				if (tx != null) {
					vertexHelper = new RenderXYQuadVertexUVHelper(dh, ch, 0, 0,
							0, 0, 0, 0, 0, 0, 1, 1).setBlight(blight);
				} else {
					vertexHelper = new RenderXYQuadVertexHelper(dh, ch, 0, 0,
							0, 0, 0, 0).setBlight(blight);
				}

				break;
			case 4:
			case 5:
				blight = 0.8F;
				if (tx != null) {
					vertexHelper = new RenderYZQuadVertexUVHelper(dh, ch, 0, 0,
							0, 0, 0, 0, 0, 0, 1, 1).setBlight(blight);
				} else {
					vertexHelper = new RenderYZQuadVertexHelper(dh, ch, 0, 0,
							0, 0, 0, 0).setBlight(blight);
				}

				break;

			default:
				break;
			}

			if (vertexHelper != null && i % 2 != 0) {
				vertexHelper = vertexHelper.setFlip();
			}

			result[i] = vertexHelper;
		}

		return result;
	}

	protected void initVertexes(RenderQuadVertexHelper[] quadRenders) {
		for (int i = 0; i < 6; i++) {
			RenderVertexHelper quadVertexHelper;
			if (quadRenders != null && quadRenders.length > i
					&& quadRenders[i] != null) {
				quadVertexHelper = quadRenders[i];
				quadVertexHelper.fromX = fromX;
				quadVertexHelper.fromY = fromY;
				quadVertexHelper.fromZ = fromZ;
				quadVertexHelper.toX = toX;
				quadVertexHelper.toY = toY;
				quadVertexHelper.toZ = toZ;
			} else {
				quadVertexHelper = RenderNoVertexHelper.HELPER;
			}

			switch (i) {
			case 0:
				topRender = quadVertexHelper;

				break;
			case 1:
				bottomRender = quadVertexHelper;

				break;
			case 2:
				northRender = quadVertexHelper;

				break;
			case 3:
				southRender = quadVertexHelper;

				break;
			case 4:
				eastRender = quadVertexHelper;

				break;
			case 5:
				westRender = quadVertexHelper;

				break;

			default:
				break;
			}
		}
	}

}
