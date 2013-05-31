package org.lo.d.commons.coords;

import net.minecraft.util.Vec3;

public class Point2DDouble extends NumPoint2D<Double> {
	public static class Factory implements NumPoint2D.Factory<Double, Point2DDouble> {
		public static final Factory instance = new Factory();

		private Factory() {
		}

		@Override
		public Point2DDouble createInstance(Double x, Double y) {
			return new Point2DDouble(x, y);
		}
	}

	public Point2DDouble(double x, double y) {
		super(x, y);
	}

	public Point2DDouble(Vec3 vec3) {
		this(vec3.xCoord, vec3.zCoord);//2DにおけるYは3DではZ
	}

	@Override
	public Point2DDouble add3DZ(Number z) {
		return addY(y);
	}

	@Override
	public Point2DDouble addPoint(NumPoint2D<?> point2d) {
		return new Point2DDouble(addedX(point2d), addedY(point2d));
	}

	@Override
	public Point2DDouble addX(Number x) {
		return new Point2DDouble(addedX(x), getY());
	}

	@Override
	public Point2DDouble addY(Number y) {
		return new Point2DDouble(getX(), addedY(y));
	}

	private double addedX(Number x) {
		return getX() + x.doubleValue();
	}

	private double addedX(NumPoint2D<?> point2d) {
		return addedX(point2d.getX());
	}

	private double addedY(Number y) {
		return getY() + y.doubleValue();
	}

	private double addedY(NumPoint2D<?> point2d) {
		return addedY(point2d.getY());
	}

}