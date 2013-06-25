package org.lo.d.commons.gui;

import java.util.Collection;
import java.util.List;

import org.lo.d.commons.coords.Rect2D;

import com.google.common.collect.Lists;

public class GridHelper<T> {
	public interface ItemDrawer<T> {
		public void draw(int xOffset, int yOffset, T item);
	}

	private final int columnsSize;
	private final int viewRowsSize;

	private final List<T> items = Lists.newArrayList();

	int rowsCount;

	int yOffset = 0;

	Rect2D gridRectBase;

	public GridHelper(int columnsSize, int viewRowsSize) {
		this.columnsSize = columnsSize;
		this.viewRowsSize = viewRowsSize;
	}

	public void addItem(T e) {
		items.add(e);
		updateRowCount();
	}

	public void addItems(Collection<? extends T> c) {
		items.addAll(c);
		updateRowCount();
	}

	public void clearItems() {
		items.clear();
		updateRowCount();
	}

	public void drawGrid(ItemDrawer<T> drawer) {
		if (items.isEmpty()) {
			return;
		}
		int offset = columnsSize * yOffset;
		if (offset > items.size() - 1) {
			return;
		}

		int offsetTo = offset + (columnsSize * viewRowsSize);
		while (offsetTo > items.size()) {
			offsetTo--;
		}

		if (offsetTo <= offset) {
			return;
		}

		int xOffset = 0;
		int yOffset = 0;
		for (T item : items.subList(offset, offsetTo)) {

			drawer.draw(xOffset, yOffset, item);

			xOffset++;
			if (xOffset >= columnsSize) {
				yOffset++;
				xOffset = 0;
			}
		}
		;
	}

	public float getScrollUnit() {
		if (rowsCount <= 1) {
			return 0;
		}
		return 1f / rowsCount - 1;
	}

	public void scrollTo(float scrollRate) {
		int yOffSetMax = rowsCount - 1;
		yOffset = (int) ((yOffSetMax * scrollRate) + 0.5);
		if (yOffset > yOffSetMax) {
			yOffset = yOffSetMax;
		}
		if (yOffset < 0) {
			yOffset = 0;
		}
	}

	private void updateRowCount() {
		if (items.isEmpty()) {
			rowsCount = 0;
			return;
		}
		rowsCount = ((items.size() - 1) / columnsSize) + 1;
	}
}