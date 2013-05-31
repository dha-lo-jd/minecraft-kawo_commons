package org.lo.d.commons.coords;

import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class Point2D extends NumPoint2D<Integer> {
	public static class Factory implements NumPoint2D.Factory<Integer, Point2D> {
		public static final Factory instance = new Factory();

		private Factory() {
		}

		@Override
		public Point2D createInstance(Integer x, Integer y) {
			return new Point2D(x, y);
		}
	}

	public Point2D(ChunkCoordinates chunkCoordinates) {
		this(chunkCoordinates.posX, chunkCoordinates.posZ);//2DにおけるYは3DではZ
	}

	public Point2D(int x, int y) {
		super(x, y);
	}

	public Point2D(NumPoint2D<?> point2d) {
		this(point2d.getX().intValue(), point2d.getY().intValue());
	}

	public Point2D(Vec3 vec3) {
		this(MathHelper.floor_double(vec3.xCoord), MathHelper.floor_double(vec3.zCoord));//2DにおけるYは3DではZ
	}

	@Override
	public Point2D add3DZ(Number z) {
		return addY(y);
	}

	@Override
	public Point2D addPoint(NumPoint2D<?> point2d) {
		return new Point2D(addedX(point2d), addedY(point2d));
	}

	@Override
	public Point2D addX(Number x) {
		return new Point2D(addedX(x), getY());
	}

	@Override
	public Point2D addY(Number y) {
		return new Point2D(getX(), addedY(y));
	}

	private int addedX(Number x) {
		return getX() + x.intValue();
	}

	private int addedX(NumPoint2D<?> point2d) {
		return addedX(point2d.getX());
	}

	private int addedY(Number y) {
		return getY() + y.intValue();
	}

	private int addedY(NumPoint2D<?> point2d) {
		return addedY(point2d.getY());
	}

}
