package org.lo.d.commons.coords;

import net.minecraft.entity.Entity;

public abstract class NumPoint3D<N extends Number, P extends NumPoint2D<N>> implements XYZ<N, P> {
	protected final P point2d;

	protected final N y;

	public NumPoint3D(N x, N y, N z, NumPoint2D.Factory<N, P> point2dFactory) {
		this(point2dFactory.createInstance(x, z), y);
	}

	public NumPoint3D(P point2d, N y) {
		this.point2d = point2d;
		this.y = y;
	}

	public abstract NumPoint3D<N, P> add(Direction dir);

	public abstract NumPoint3D<N, P> addPoint(NumPoint3D<?, ?> point3d);

	public abstract NumPoint3D<N, P> addX(Number x);

	public abstract NumPoint3D<N, P> addY(Number y);

	public abstract NumPoint3D<N, P> addZ(Number z);

	public double distanceToSq(NumPoint3D<?, ?> dest) {
		double d = point2d.distanceToSq(dest.point2d);
		double y = getY().doubleValue() - dest.getY().doubleValue();

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
		NumPoint3D<?, ?> other = (NumPoint3D<?, ?>) obj;
		if (point2d == null) {
			if (other.point2d != null) {
				return false;
			}
		} else if (!point2d.equals(other.point2d)) {
			return false;
		}
		if (y == null) {
			if (other.y != null) {
				return false;
			}
		} else if (!y.equals(other.y)) {
			return false;
		}
		return true;
	}

	public abstract NumPoint3D<N, P> extendedPoint(N radius);

	public P getPoint2d() {
		return point2d;
	}

	@Override
	public N getX() {
		return point2d.getX();
	}

	@Override
	public P getXY() {
		return point2d;
	}

	@Override
	public N getY() {
		return y;
	}

	@Override
	public N getZ() {
		return point2d.get3DZ();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((point2d == null) ? 0 : point2d.hashCode());
		result = prime * result + ((y == null) ? 0 : y.hashCode());
		return result;
	}

	public void setEntityPosition(Entity entity) {
		entity.setPosition(getX().doubleValue(), getY().doubleValue(), getZ().doubleValue());
	}

	@Override
	public String toString() {
		return "Point3D [x=" + point2d.getX() + ", y=" + y + ", z=" + point2d.getY() + "]";
	}

}