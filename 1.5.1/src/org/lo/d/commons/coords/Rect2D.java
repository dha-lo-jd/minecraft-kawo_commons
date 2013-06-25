package org.lo.d.commons.coords;

public class Rect2D {
	private final Point2D topLeftPoint;

	private final Point2D bottomRightPoint;

	public Rect2D(int x, int y, int width, int height) {
		this(new Point2D(x, y), width, height);
	}

	public Rect2D(Point2D topLeftPoint, int width, int height) {
		this(topLeftPoint, topLeftPoint.addX(width).addY(height));
	}

	public Rect2D(Point2D topLeftPoint, Point2D bottomRightPoint) {
		this.topLeftPoint = topLeftPoint;
		this.bottomRightPoint = bottomRightPoint;
	}

	public Point2D getBottomRightPoint() {
		return bottomRightPoint;
	}

	public Point2D getTopLeftPoint() {
		return topLeftPoint;
	}

	public boolean isInRect(Point2D point) {
		int pX = point.getX();
		int pY = point.getY();

		int top = topLeftPoint.getY();
		int left = topLeftPoint.getX();

		int bottom = bottomRightPoint.getY();
		int right = bottomRightPoint.getX();

		if (pY < top || bottom < pY || pX < left || right < pX) {
			return false;
		} else {
			return true;
		}

	}
}
