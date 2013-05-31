package org.lo.d.commons.coords;

import net.minecraft.util.Vec3;

public class Point3DDouble extends NumPoint3D<Double, Point2DDouble> {

	public Point3DDouble(Double x, Double y, Double z) {
		super(x, y, z, Point2DDouble.Factory.instance);
	}

	public Point3DDouble(Point2DDouble point2d, Double y) {
		super(point2d, y);
	}

	public Point3DDouble(Vec3 vec3) {
		this(new Point2DDouble(vec3), vec3.yCoord);
	}

	@Override
	public Point3DDouble add(Direction dir) {
		return addPoint(Point3D.map.get(dir));
	}

	@Override
	public Point3DDouble addPoint(NumPoint3D<?, ?> point3d) {
		return new Point3DDouble(point2d.addPoint(point3d.point2d), addedY(point3d));
	}

	@Override
	public Point3DDouble addX(Number x) {
		return new Point3DDouble(point2d.addX(x), getY());
	}

	@Override
	public Point3DDouble addY(Number y) {
		return new Point3DDouble(point2d, addedY(y));
	}

	@Override
	public Point3DDouble addZ(Number z) {
		return new Point3DDouble(point2d.add3DZ(z), getY());
	}

	@Override
	public Point3DDouble extendedPoint(Double radius) {
		return new Point3DDouble(getX() * radius, getY() * radius, getZ() * radius);
	}

	private double addedY(Number y) {
		return getY() + y.intValue();
	}

	private double addedY(NumPoint3D<?, ?> point3d) {
		return addedY(point3d.getY());
	}

}