package org.lo.d.commons.coords;

import net.minecraft.entity.Entity;

public abstract class NumPoint2D<N extends Number> implements XY<N> {
	public interface Factory<N extends Number, P extends NumPoint2D<N>> {
		P createInstance(N x, N y);
	}

	protected final N x, y;

	public NumPoint2D(N x, N y) {
		this.x = x;
		this.y = y;
	}

	public abstract NumPoint2D<N> add3DZ(Number z);

	public abstract NumPoint2D<N> addPoint(NumPoint2D<?> point2d);

	public abstract NumPoint2D<N> addX(Number x);

	public abstract NumPoint2D<N> addY(Number y);

	public double distanceToSq(NumPoint2D<?> dest) {
		double x = getX().doubleValue() - dest.getX().doubleValue();
		double y = getY().doubleValue() - dest.getY().doubleValue();

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
		NumPoint2D<?> other = (NumPoint2D<?>) obj;
		if (x == null) {
			if (other.x != null) {
				return false;
			}
		} else if (!x.equals(other.x)) {
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

	/**
	 * 2DにおけるYは3DではZ
	 * @return
	 */
	@Override
	public N get3DZ() {
		return getY();
	}

	@Override
	public N getX() {
		return x;
	}

	@Override
	public N getY() {
		return y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((x == null) ? 0 : x.hashCode());
		result = prime * result + ((y == null) ? 0 : y.hashCode());
		return result;
	}

	public void setEntityPosition(Entity entity) {
		entity.setPosition(getX().doubleValue(), entity.posY, get3DZ().doubleValue());
	}

	@Override
	public String toString() {
		return "Point2D [x=" + x + ", y=" + y + "]";
	}

	private double distanceToSq(double x, double y) {
		return (x * x) + (y * y);
	}

}