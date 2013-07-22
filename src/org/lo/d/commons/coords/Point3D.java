package org.lo.d.commons.coords;

import java.util.Map;

import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

import com.google.common.collect.Maps;

public class Point3D extends NumPoint3D<Integer, Point2D> {
	public static Map<Direction, Point3D> map;
	static {
		Map<Direction, Point3D> m = Maps.newEnumMap(Direction.class);
		m.put(Direction.TOP, new Point3D(0, 1, 0));
		m.put(Direction.BOTTOM, new Point3D(0, -1, 0));
		m.put(Direction.FRONT, new Point3D(0, 0, 1));
		m.put(Direction.BACK, new Point3D(0, 0, -1));
		m.put(Direction.RIGHT, new Point3D(1, 0, 0));
		m.put(Direction.LEFT, new Point3D(-1, 0, 0));
		map = m;
	}

	public Point3D(ChunkCoordinates chunkCoordinates) {
		this(new Point2D(chunkCoordinates), chunkCoordinates.posY);
	}

	public Point3D(Integer x, Integer y, Integer z) {
		super(x, y, z, Point2D.Factory.instance);
	}

	public Point3D(NumPoint3D<?, ?> point3d) {
		this(point3d.getX().intValue(), point3d.getY().intValue(), point3d.getZ().intValue());
	}

	public Point3D(Point2D point2d, Integer y) {
		super(point2d, y);
	}

	public Point3D(Vec3 vec3) {
		this(new Point2D(vec3), MathHelper.floor_double(vec3.yCoord));
	}

	@Override
	public Point3D add(Direction dir) {
		return addPoint(map.get(dir));
	}

	@Override
	public Point3D addPoint(NumPoint3D<?, ?> point3d) {
		return new Point3D(point2d.addPoint(point3d.point2d), addedY(point3d));
	}

	@Override
	public Point3D addX(Number x) {
		return new Point3D(point2d.addX(x), getY());
	}

	@Override
	public Point3D addY(Number y) {
		return new Point3D(point2d, addedY(y));
	}

	@Override
	public Point3D addZ(Number z) {
		return new Point3D(point2d.add3DZ(z), getY());
	}

	@Override
	public Point3D extendedPoint(Integer radius) {
		return new Point3D(getX() * radius, getY() * radius, getZ() * radius);
	}

	@Override
	public Point3D flip() {
		return new Point3D(-getX(), -getY(), -getZ());
	}

	private int addedY(Number y) {
		return getY() + y.intValue();
	}

	private int addedY(NumPoint3D<?, ?> point3d) {
		return addedY(point3d.getY());
	}

}