package org.lo.d.commons.coords;

import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class Point3D {
	private final Point2D point2d;
	private final int y;

	public Point3D(int x, int y, int z) {
		this(new Point2D(x, z), y);
	}

	public Point3D(Point2D point, int y) {
		point2d = point;
		this.y = y;
	}

	public Point3D(Vec3 vec3) {
		this(new Point2D(vec3), MathHelper.floor_double(vec3.zCoord));
	}

	public int distanceToSq(Point3D dest) {
		int d = point2d.distanceToSq(dest.point2d);
		int y = getY() - dest.getY();

		return (d * d) + (y * y);
	}

	public double distanceToSq(Vec3 vec3) {
		double d = point2d.distanceToSq(vec3);
		double y = getY() - vec3.yCoord;

		return d + (y * y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Point3D other = (Point3D) obj;
		if (point2d == null) {
			if (other.point2d != null) {
				return false;
			}
		} else if (!point2d.equals(other.point2d)) {
			return false;
		}
		if (y != other.y) {
			return false;
		}
		return true;
	}

	public Point3D extendedPoint(int radius) {
		return new Point3D(getX() * radius, getY() * radius, getZ() * radius);
	}

	public int getX() {
		return point2d.getX();
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return point2d.getY();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((point2d == null) ? 0 : point2d.hashCode());
		result = prime * result + y;
		return result;
	}

	@Override
	public String toString() {
		return "Point3D [x=" + point2d.getX() + ", y=" + y + ", z=" + point2d.getY() + "]";
	}

}