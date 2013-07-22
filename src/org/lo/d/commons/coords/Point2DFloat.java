package org.lo.d.commons.coords;


public class Point2DFloat extends NumPoint2D<Float> {
	public static class Factory implements NumPoint2D.Factory<Float, Point2DFloat> {
		public static final Factory instance = new Factory();

		private Factory() {
		}

		@Override
		public Point2DFloat createInstance(Float x, Float y) {
			return new Point2DFloat(x, y);
		}
	}

	public Point2DFloat(float x, float y) {
		super(x, y);
	}

	@Override
	public Point2DFloat add3DZ(Number z) {
		return addY(y);
	}

	@Override
	public Point2DFloat addPoint(NumPoint2D<?> point2d) {
		return new Point2DFloat(addedX(point2d), addedY(point2d));
	}

	@Override
	public Point2DFloat addX(Number x) {
		return new Point2DFloat(addedX(x), getY());
	}

	@Override
	public Point2DFloat addY(Number y) {
		return new Point2DFloat(getX(), addedY(y));
	}

	private float addedX(Number x) {
		return getX() + x.floatValue();
	}

	private float addedX(NumPoint2D<?> point2d) {
		return addedX(point2d.getX());
	}

	private float addedY(Number y) {
		return getY() + y.floatValue();
	}

	private float addedY(NumPoint2D<?> point2d) {
		return addedY(point2d.getY());
	}

}