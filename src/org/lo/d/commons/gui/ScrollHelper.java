package org.lo.d.commons.gui;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

import org.lo.d.commons.coords.Point2D;
import org.lo.d.commons.coords.Point2DFloat;
import org.lo.d.commons.coords.Rect2D;
import org.lo.d.commons.gl.SafetyGL;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class ScrollHelper {
	public static final ResourceLocation DEFAULT_SCROLL_BAR = new ResourceLocation("lmm_ex",
			"textures/gui/scroll_bar.png");
	public static final ResourceLocation DEFAULT_SCROLL_BAR_BG = new ResourceLocation("lmm_ex",
			"textures/gui/scroll_bar_bg.png");

	private static final int BAR_WIDTH = 12;
	private static final int BAR_HEIGHT = 15;
	private static final int BAR_OFFSET_TOP = 7;
	private static final int BAR_OFFSET_BOTTOM = 7;

	private static final float BAR_TEXTURE_U_UNIT = 0.5f;
	private static final float BAR_TEXTURE_V_UNIT = 0.5f;

	private static final Point2DFloat BAR_ICON_INDEX_ENABLE = new Point2DFloat(0, 0);
	private static final Point2DFloat BAR_ICON_INDEX_DISABLE = new Point2DFloat(0, 0.5f);
	private static final Point2DFloat BAR_ICON_INDEX_CLICKED = new Point2DFloat(0.5f, 0);

	private Rect2D scrollBarRect;

	private int scrollTopY;
	private final int scrollSize;
	private final int scrollBarSize;

	private final ResourceLocation texture;
	private final ResourceLocation bgTexture;

	boolean enable = true;

	boolean wasClicking = false;
	boolean isScrolling = false;

	private int currentScrollY = 0;

	public float zLevel = 0;

	public ScrollHelper(int scrollBarSize, ResourceLocation texture) {
		this(scrollBarSize, texture, null);
	}

	public ScrollHelper(int scrollBarSize, ResourceLocation texture, ResourceLocation bgTexture) {
		this.texture = texture;
		this.bgTexture = bgTexture;
		this.scrollBarSize = scrollBarSize;
		scrollSize = scrollBarSize - BAR_OFFSET_TOP - BAR_OFFSET_BOTTOM;
	}

	public void drawScrollBar(int mouseX, int mouseY, float renderParticleTicks, final TextureManager renderEngine) {
		SafetyGL.safetyGLProcess(new SafetyGL.Processor() {
			@Override
			public void process(SafetyGL safetyGL) {
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				safetyGL.disableStandardItemLighting();
				renderEngine.func_110577_a(texture);
				Point2D drawPoint = scrollBarRect.getTopLeftPoint();
				drawPoint = drawPoint.addY(currentScrollY);
				int drawLeft = drawPoint.getX();
				int drawTop = drawPoint.getY();
				Point2DFloat textureUV = getTextureUV();
				float u = textureUV.getX();
				float v = textureUV.getY();
				doDrawScrollBar(drawLeft, drawTop, BAR_WIDTH, BAR_HEIGHT, u, v);
			}
		});
	}

	public void drawScrollBarBackGround(int mouseX, int mouseY, float renderParticleTicks,
			final TextureManager renderEngine) {
		if (bgTexture == null) {
			return;
		}
		SafetyGL.safetyGLProcess(new SafetyGL.Processor() {
			@Override
			public void process(SafetyGL safetyGL) {
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				safetyGL.disableStandardItemLighting();
				renderEngine.func_110577_a(bgTexture);
				Point2D drawPoint = scrollBarRect.getTopLeftPoint().addX(-1).addY(-1);
				int drawLeft = drawPoint.getX();
				int drawTop = drawPoint.getY();
				doDrawScrollBarBG(drawLeft, drawTop, BAR_WIDTH + 2, 1, 0, 1f / 6f);
				doDrawScrollBarBG(drawLeft, drawTop + 1, BAR_WIDTH + 2, scrollBarSize, 1f / 6f, 4f / 6f);
				doDrawScrollBarBG(drawLeft, drawTop + scrollBarSize, BAR_WIDTH + 2, 1, 5f / 6f, 1f / 6f);
			}
		});
	}

	public float getCurrentScrollRate() {
		float currentScrollRate = (float) currentScrollY / scrollSize;

		return round(currentScrollRate);
	}

	public void setCurrentScrollY(float rate) {
		rate = round(rate);

		setCurrentScrollY((int) (scrollSize * rate));
	}

	public void setCurrentScrollY(int y) {
		currentScrollY = y;
		if (currentScrollY < 0) {
			currentScrollY = 0;
		}

		if (currentScrollY > scrollSize) {
			currentScrollY = scrollSize;
		}
	}

	public void updateOnDrawScreen(int mouseX, int mouseY) {
		Point2D mousePoint = new Point2D(mouseX, mouseY);

		boolean flag = Mouse.isButtonDown(0);

		if (!wasClicking && flag && scrollBarRect.isInRect(mousePoint)) {
			isScrolling = enable;
		}

		if (!flag) {
			isScrolling = false;
		}

		wasClicking = flag;

		if (isScrolling) {
			updateCurrentScrollY(mouseY);
		}
	}

	public void updateScrollRect(Point2D drawPoint) {
		scrollBarRect = new Rect2D(drawPoint, BAR_WIDTH, scrollBarSize);
		scrollTopY = scrollBarRect.getTopLeftPoint().getY() + BAR_OFFSET_TOP;
	}

	private Point2DFloat getTextureUV() {
		if (!enable) {
			return BAR_ICON_INDEX_DISABLE;
		} else if (isScrolling) {
			return BAR_ICON_INDEX_CLICKED;
		}
		return BAR_ICON_INDEX_ENABLE;
	}

	private float round(float f) {
		if (f < 0.0F) {
			f = 0.0F;
		}
		if (f > 1.0F) {
			f = 1.0F;
		}
		return f;
	}

	private void updateCurrentScrollY(int mouseY) {
		//カーソル画像の中央部分のY座標が基準
		setCurrentScrollY(mouseY - scrollTopY);
	}

	protected void doDrawScrollBar(int x, int y, int width, int height, float u, float v) {
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(x + 0, y + height, zLevel, u, v + BAR_TEXTURE_V_UNIT);
		tessellator.addVertexWithUV(x + width, y + height, zLevel, u + BAR_TEXTURE_U_UNIT, v + BAR_TEXTURE_V_UNIT);
		tessellator.addVertexWithUV(x + width, y + 0, zLevel, u + BAR_TEXTURE_U_UNIT, v);
		tessellator.addVertexWithUV(x + 0, y + 0, zLevel, u, v);
		tessellator.draw();
	}

	protected void doDrawScrollBarBG(int x, int y, int width, int height, float v, float vSize) {
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(x + 0, y + height, zLevel, 0, v + vSize);
		tessellator.addVertexWithUV(x + width, y + height, zLevel, 1, v + vSize);
		tessellator.addVertexWithUV(x + width, y + 0, zLevel, 1, v);
		tessellator.addVertexWithUV(x + 0, y + 0, zLevel, 0, v);
		tessellator.draw();
	}
}