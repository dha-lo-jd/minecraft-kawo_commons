package org.lo.d.commons.coords;

import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class Point2D {
	private final int x, y;

	public Point2D(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Point2D(Vec3 vec3) {
		this(MathHelper.floor_double(vec3.xCoord), MathHelper.floor_double(vec3.yCoord));
	}

	public double distanceToSq(double x, double y) {
		return (x * x) + (y * y);
	}

	public int distanceToSq(Point2D dest) {
		int x = getX() - dest.getX();
		int y = getY() - dest.getY();

		return distanceToSq(x, y);
	}

	public double distanceToSq(Vec3 vec3) {
		double toX = vec3.xCoord;
		double toY = vec3.zCoord;//3次元におけるZが2次元でのY

		double x = getX() - toX;
		double y = getY() - toY;

		return distanceToSq(x, y);

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
		Point2D other = (Point2D) obj;
		if (x != other.x) {
			return false;
		}
		if (y != other.y) {
			return false;
		}
		return true;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public String toString() {
		return "Point2D [x=" + x + ", y=" + y + "]";
	}

	private int distanceToSq(int x, int y) {
		return (x * x) + (y * y);
	}

}