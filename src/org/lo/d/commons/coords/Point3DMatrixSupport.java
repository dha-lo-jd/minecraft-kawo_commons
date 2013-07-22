package org.lo.d.commons.coords;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class Point3DMatrixSupport {

	public static class Point3DMatrixCircuit implements Iterable<Point3D> {

		private final List<Point3D> points;

		public Point3DMatrixCircuit(int radius) {
			List<Point3D> list = Lists.newArrayList();
			for (int i = 0; i <= radius; i++) {
				for (int j : new int[] { 1, -1 }) {
					if (i == 0 && j != 1) {
						break;
					}
					int y = i * j;
					int radius2D = radius - i;
					if (radius2D != 0) {
						for (Point2D point2d : Point2DMatrixSupport.getCircuit2D(radius2D)) {
							list.add(new Point3D(point2d, y));
						}
					} else {
						list.add(new Point3D(0, y, 0));
					}
				}
			}
			points = list;
		}

		@Override
		public Iterator<Point3D> iterator() {
			return points.iterator();
		}
	}

	private static final Map<Integer, Point3DMatrixCircuit> cirsuitMap = Maps.newHashMap();

	private static final Map<Integer, Iterable<Point3D>> matrixMap = Maps.newHashMap();

	public static Iterable<Point3D> getAllSurfaceAxis() {
		return getNearestPointMatrix3D(1);
	}

	public static Iterable<Point3D> getCircuit3D(int radius) {
		if (!cirsuitMap.containsKey(radius)) {
			cirsuitMap.put(radius, new Point3DMatrixCircuit(radius));
		}
		return cirsuitMap.get(radius);
	}

	public static Iterable<Point3D> getNearestPointMatrix3D(int radius) {
		if (radius < 1) {
			return Lists.newArrayList();
		}
		if (!matrixMap.containsKey(radius)) {
			List<Point3D> list = Lists.newArrayList();
			list.addAll(Lists.newArrayList(getNearestPointMatrix3D(radius - 1)));
			list.addAll(Lists.newArrayList(getCircuit3D(radius)));
			matrixMap.put(radius, list);
		}
		return matrixMap.get(radius);
	}

}
