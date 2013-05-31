package org.lo.d.commons.coords;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class Point2DMatrixSupport {

	public static class Point2DMatrixCircuit implements Iterable<Point2D> {
		private interface CircuitWalker {
			int getX(int radius, int cursor);

			int getY(int radius, int cursor);
		}

		private static final CircuitWalker[] walkers = {
				new CircuitWalker() {//X0Y+ から X+Y0 へ
					@Override
					public int getX(int radius, int cursor) {
						return cursor;
					}

					@Override
					public int getY(int radius, int cursor) {
						return radius - cursor;
					}
				},
				new CircuitWalker() {//X+Y0 から X0Y- へ
					@Override
					public int getX(int radius, int cursor) {
						return radius - cursor;
					}

					@Override
					public int getY(int radius, int cursor) {
						return -cursor;
					}
				},
				new CircuitWalker() {//X0Y- から X-Y0 へ
					@Override
					public int getX(int radius, int cursor) {
						return -cursor;
					}

					@Override
					public int getY(int radius, int cursor) {
						return -(radius - cursor);
					}
				},
				new CircuitWalker() {//X-Y0 から X0Y+ へ
					@Override
					public int getX(int radius, int cursor) {
						return -(radius - cursor);
					}

					@Override
					public int getY(int radius, int cursor) {
						return cursor;
					}
				},
		};

		private final List<Point2D> points;

		public Point2DMatrixCircuit(int radius) {
			List<Point2D> list = Lists.newArrayList();
			for (CircuitWalker walker : walkers) {
				for (int i = 0; i < radius; i++) {
					int x = walker.getX(radius, i);
					int y = walker.getY(radius, i);
					list.add(new Point2D(x, y));
				}
			}
			points = list;
		}

		@Override
		public Iterator<Point2D> iterator() {
			return points.iterator();
		}
	}

	private static final Map<Integer, Point2DMatrixCircuit> cirsuitMap = Maps.newHashMap();

	private static final Map<Integer, Iterable<Point2D>> matrixMap = Maps.newHashMap();

	public static Iterable<Point2D> getCircuit2D(int radius) {
		if (!cirsuitMap.containsKey(radius)) {
			cirsuitMap.put(radius, new Point2DMatrixCircuit(radius));
		}
		return cirsuitMap.get(radius);
	}

	public static Iterable<Point2D> getNearestPointMatrix2D(int radius) {
		if (radius < 1) {
			return Lists.newArrayList();
		}
		if (!matrixMap.containsKey(radius)) {
			List<Point2D> list = Lists.newArrayList();
			list.addAll(Lists.newArrayList(getNearestPointMatrix2D(radius - 1)));
			list.addAll(Lists.newArrayList(getCircuit2D(radius)));
			matrixMap.put(radius, list);
		}
		return matrixMap.get(radius);
	}

}
