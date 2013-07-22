package org.lo.d.commons.coords;

public interface XYZ<N extends Number, P extends XY<N>> {
	public N getX();

	public P getXY();

	public N getY();

	public N getZ();
}
