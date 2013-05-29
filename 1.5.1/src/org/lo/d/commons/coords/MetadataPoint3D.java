package org.lo.d.commons.coords;

import java.util.List;

import com.google.common.collect.Lists;

public class MetadataPoint3D extends Point3D {
	public interface AxisPointFactory {
		MetadataPoint3D create(int axis);
	}

	public static class PointXAxis extends MetadataPoint3D {

		public static final AxisPointFactory FACTORY = new AxisPointFactory() {
			@Override
			public MetadataPoint3D create(int axis) {
				return new PointXAxis(axis);
			}
		};

		public PointXAxis(int x) {
			super(x, 0, 0, x < 0 ? 4 : 5);
		}
	}

	public static class PointYAxis extends MetadataPoint3D {
		public static final AxisPointFactory FACTORY = new AxisPointFactory() {
			@Override
			public MetadataPoint3D create(int axis) {
				return new PointYAxis(axis);
			}
		};

		public PointYAxis(int y) {
			super(0, y, 0, y < 0 ? 0 : 1);
		}
	}

	public static class PointZAxis extends MetadataPoint3D {
		public static final AxisPointFactory FACTORY = new AxisPointFactory() {
			@Override
			public MetadataPoint3D create(int axis) {
				return new PointZAxis(axis);
			}
		};

		public PointZAxis(int z) {
			super(0, 0, z, z < 0 ? 2 : 3);
		}
	}

	protected static final AxisPointFactory[] axis3DFactorys = {
			PointXAxis.FACTORY,
			PointYAxis.FACTORY,
			PointZAxis.FACTORY,
	};

	public static final MetadataPoint3D[] AllSurfacePoints;
	static {
		List<Point3D> list = Lists.newArrayList();
		int[] direction = { -1, 1 };
		for (AxisPointFactory factory : axis3DFactorys) {
			for (int dir : direction) {
				list.add(factory.create(dir));
			}
		}
		AllSurfacePoints = list.toArray(new MetadataPoint3D[] {});
	}

	public final int metadataSurface;

	public MetadataPoint3D(int x, int y, int z, int m) {
		super(x, y, z);
		metadataSurface = m;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		MetadataPoint3D other = (MetadataPoint3D) obj;
		if (metadataSurface != other.metadataSurface) {
			return false;
		}
		return true;
	}

	@Override
	public MetadataPoint3D extendedPoint(int radius) {
		return new MetadataPoint3D(getX() * radius, getY() * radius, getZ() * radius, metadataSurface);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + metadataSurface;
		return result;
	}
}