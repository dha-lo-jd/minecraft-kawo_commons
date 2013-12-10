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

/**
 * 可動軸(Axis)上にあるハンドルバー(Bar)をマウスでドラッグして可動域内で動かすスクロールバー
 */
public abstract class ScrollHelperBase {

	/**
	 * ハンドルバーの画像ファイルの単位u値
	 * ハンドルバーの画像ファイルを4分割するので0.5で固定
	 */
	private static final float BAR_TEXTURE_U_UNIT = 0.5f;

	/**
	 * ハンドルバーの画像ファイルの単位v値
	 * ハンドルバーの画像ファイルを4分割するので0.5で固定
	 */
	private static final float BAR_TEXTURE_V_UNIT = 0.5f;

	/**
	 * ハンドルバーが通常状態の時のuvセット
	 * (画像ファイルを4分割した時の左上を使用)
	 */
	private static final Point2DFloat BAR_ICON_INDEX_ENABLE = new Point2DFloat(0, 0);
	/**
	 * ハンドルバーが無効状態の時のuvセット
	 * (画像ファイルを4分割した時の左下を使用)
	 */
	private static final Point2DFloat BAR_ICON_INDEX_DISABLE = new Point2DFloat(0, BAR_TEXTURE_V_UNIT);
	/**
	 * ハンドルバーがドラッグ状態の時のuvセット
	 * (画像ファイルを4分割した時の右上を使用)
	 */
	private static final Point2DFloat BAR_ICON_INDEX_CLICKED = new Point2DFloat(BAR_TEXTURE_U_UNIT, 0);

	/**
	 * 画面全体の座標で見たスクロール描画域の座標である矩形
	 */
	protected Rect2D scrollBarRect;

	/**
	 * スクロール可動域の最小端
	 * (ウィンドウサイズ変更等で可変するのでfinalじゃない)
	 */
	protected int scrollAxisOffsetMin;

	/**
	 * スクロール可動域サイズ
	 * スクロール描画域からバーの最小端と最大端オフセットを引いたもの
	 */
	protected final int scrollSize;

	/**
	 * スクロール描画域サイズ
	 * 論理上のスクロール可動域とは異なる
	 */
	protected final int scrollBarSize;

	/**
	 * ハンドルバーの画像
	 */
	protected final ResourceLocation texture;

	/**
	 * スクロールバーの画像
	 */
	protected final ResourceLocation bgTexture;

	/**
	 * スクロールバーが操作可能
	 */
	protected boolean enable = true;

	/**
	 * マウスがスクロールバーの上でクリックされている
	 */
	protected boolean wasClicking = false;

	/**
	 * スクロールバーがドラッグされている
	 */
	protected boolean isScrolling = false;

	/**
	 * 可動域上で見たハンドルバーの現在位置
	 * (スクロール可動域の最小端を0とする)
	 */
	protected int currentScrollAxisOffset = 0;

	/**
	 * GUI上のzIndex(Web系であることをｱｯﾋﾟﾙ)
	 */
	public float zLevel = 0;

	public ScrollHelperBase(int scrollBarSize, ResourceLocation texture) {
		this(scrollBarSize, texture, null);
	}

	public ScrollHelperBase(int scrollBarSize, ResourceLocation texture, ResourceLocation bgTexture) {
		this.texture = texture;
		this.bgTexture = bgTexture;
		this.scrollBarSize = scrollBarSize;
		scrollSize = scrollBarSize - getBarAxisOffsetMin() - getBarAxisOffsetMax();
	}

	/**
	 * ハンドルバー描画位置を決める
	 * (左上の座標)
	 */
	protected abstract Point2D addOffsetAxisPos(Point2D point);

	/**
	 * ハンドルバー描画
	 * 実食
	 */
	protected void doDrawScrollBar(int x, int y, int width, int height, float u, float v) {
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(x + 0, y + height, zLevel, u, v + BAR_TEXTURE_V_UNIT);
		tessellator.addVertexWithUV(x + width, y + height, zLevel, u + BAR_TEXTURE_U_UNIT, v + BAR_TEXTURE_V_UNIT);
		tessellator.addVertexWithUV(x + width, y + 0, zLevel, u + BAR_TEXTURE_U_UNIT, v);
		tessellator.addVertexWithUV(x + 0, y + 0, zLevel, u, v);
		tessellator.draw();
	}

	/**
	 * スクロールバー描画
	 * 実食
	 */
	protected void doDrawScrollBarBG(int x, int y, int width, int height, float v, float vSize) {
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(x + 0, y + height, zLevel, 0, v + vSize);
		tessellator.addVertexWithUV(x + width, y + height, zLevel, 1, v + vSize);
		tessellator.addVertexWithUV(x + width, y + 0, zLevel, 1, v);
		tessellator.addVertexWithUV(x + 0, y + 0, zLevel, 0, v);
		tessellator.draw();
	}

	/**
	 * スクロールバー可動域の描画
	 */
	protected abstract void doDrawScrollBarBGAxisMain(int drawLeft, int drawTop);

	/**
	 * スクロールバー最小端の描画
	 */
	protected abstract void doDrawScrollBarBGAxisMax(int drawLeft, int drawTop);

	/**
	 * スクロールバー最小端の描画
	 */
	protected abstract void doDrawScrollBarBGAxisMin(int drawLeft, int drawTop);

	/**
	 * ハンドルバー描画
	 * 位置決め
	 */
	public void drawScrollBar(int mouseX, int mouseY, float renderParticleTicks, final TextureManager renderEngine) {
		SafetyGL.safetyGLProcess(new SafetyGL.Processor() {
			@Override
			public void process(SafetyGL safetyGL) {
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				safetyGL.disableStandardItemLighting();
				renderEngine.func_110577_a(texture);
				Point2D drawPoint = scrollBarRect.getTopLeftPoint();
				drawPoint = addOffsetAxisPos(drawPoint);
				int drawLeft = drawPoint.getX();
				int drawTop = drawPoint.getY();
				Point2DFloat textureUV = getTextureUV();
				float u = textureUV.getX();
				float v = textureUV.getY();
				doDrawScrollBar(drawLeft, drawTop, getBarWidth(), getBarHeight(), u, v);
			}
		});
	}

	/**
	 * スクロールバー描画
	 * 位置決め
	 *
	 */
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
				doDrawScrollBarBGAxisMin(drawLeft, drawTop);
				doDrawScrollBarBGAxisMax(drawLeft, drawTop);
				doDrawScrollBarBGAxisMain(drawLeft, drawTop);
			}
		});
	}

	/**
	 * 可動軸上のハンドルバー画像の中央位置から最大端へのオフセット値
	 */
	protected abstract int getBarAxisOffsetMax();

	/**
	 * 可動軸上のハンドルバー画像の最小端から中央位置へのオフセット値
	 */
	protected abstract int getBarAxisOffsetMin();

	/**
	 * ハンドルバー画像の縦幅
	 */
	protected abstract int getBarHeight();

	/**
	 * ハンドルバー画像の横幅
	 */
	protected abstract int getBarWidth();

	/**
	 * 可動域サイズ全体を1とする比率でみたハンドルバーの位置
	 * (0.0f～1.0f)
	 */
	public float getCurrentScrollRate() {
		float currentScrollRate = (float) currentScrollAxisOffset / scrollSize;

		return round(currentScrollRate);
	}

	/**
	 * 描画するハンドルバー画像をuv値で選ぶ
	 */
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

	/**
	 * 可動域サイズ全体を1とする比率でみたrate値の位置にハンドルバーを動かす
	 */
	public void setCurrentScrollAxis(float rate) {
		rate = round(rate);

		setCurrentScrollAxis((int) (scrollSize * rate));
	}

	/**
	 * 可動域最小端を0として描画位置で見たaxisPosの位置にハンドルバーを動かす
	 */
	public void setCurrentScrollAxis(int axisPos) {
		currentScrollAxisOffset = axisPos;
		if (currentScrollAxisOffset < 0) {
			currentScrollAxisOffset = 0;
		}

		if (currentScrollAxisOffset > scrollSize) {
			currentScrollAxisOffset = scrollSize;
		}
	}

	/**
	 * 座標軸上のマウス位置にハンドルバーを動かす
	 * カーソル画像の中央部分のY座標が基準
	 */
	private void updateCurrentScrollY(int mouseAxisPos) {
		setCurrentScrollAxis(mouseAxisPos - scrollAxisOffsetMin);
	}

	/**
	 * GUI更新イベント
	 * マウス操作のハンドリング
	 * バニラの処理をパクった
	 */
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

	/**
	 * スクリーン更新イベント
	 * スクロールバーの位置とかスクロール可動域上端の位置を決める
	 */
	public void updateScrollRect(Point2D drawPoint) {
		scrollBarRect = new Rect2D(drawPoint, getBarWidth(), scrollBarSize);
		scrollAxisOffsetMin = scrollBarRect.getTopLeftPoint().getY() + getBarAxisOffsetMin();
	}
}