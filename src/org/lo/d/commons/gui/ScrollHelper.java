package org.lo.d.commons.gui;

import net.minecraft.util.ResourceLocation;

import org.lo.d.commons.coords.Point2D;

public class ScrollHelper extends ScrollHelperBase {
	public static final ResourceLocation DEFAULT_SCROLL_BAR = new ResourceLocation("lmm_ex",
			"textures/gui/scroll_bar.png");

	public static final ResourceLocation DEFAULT_SCROLL_BAR_BG = new ResourceLocation("lmm_ex",
			"textures/gui/scroll_bar_bg.png");

	private static final int BAR_WIDTH = 12;
	private static final int BAR_HEIGHT = 15;

	private static final int BAR_OFFSET_TOP = 7;
	private static final int BAR_OFFSET_BOTTOM = 7;

	public ScrollHelper(int scrollBarSize, ResourceLocation texture) {
		super(scrollBarSize, texture);
	}

	public ScrollHelper(int scrollBarSize, ResourceLocation texture, ResourceLocation bgTexture) {
		super(scrollBarSize, texture, bgTexture);
	}

	@Override
	protected Point2D addOffsetAxisPos(Point2D point) {
		return point.addY(currentScrollAxisOffset);
	}

	@Override
	protected void doDrawScrollBarBGAxisMain(int drawLeft, int drawTop) {
		doDrawScrollBarBG(drawLeft, drawTop + scrollBarSize, BAR_WIDTH + 2, 1, 5f / 6f, 1f / 6f);
	}

	@Override
	protected void doDrawScrollBarBGAxisMax(int drawLeft, int drawTop) {
		doDrawScrollBarBG(drawLeft, drawTop + 1, BAR_WIDTH + 2, scrollBarSize, 1f / 6f, 4f / 6f);

	}

	@Override
	protected void doDrawScrollBarBGAxisMin(int drawLeft, int drawTop) {
		doDrawScrollBarBG(drawLeft, drawTop, BAR_WIDTH + 2, 1, 0, 1f / 6f);

	}

	@Override
	protected int getBarAxisOffsetMax() {
		return BAR_OFFSET_BOTTOM;
	}

	@Override
	protected int getBarAxisOffsetMin() {
		return BAR_OFFSET_TOP;
	}

	@Override
	protected int getBarHeight() {
		return BAR_HEIGHT;
	}

	@Override
	protected int getBarWidth() {
		return BAR_WIDTH;
	}

}