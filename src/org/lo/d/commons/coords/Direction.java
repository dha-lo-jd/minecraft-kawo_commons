package org.lo.d.commons.coords;

import java.util.Map;

import com.google.common.collect.Maps;

public enum Direction {
	TOP(0),
	BOTTOM(1),
	FRONT(2),
	BACK(3),
	RIGHT(4),
	LEFT(5), ;
	private final int value;

	private static final Map<Integer, Direction> map;

	private static final Map<Direction, Direction> reverseMap;

	private static final Map<Direction, Direction> nextMap;
	static {
		{
			Map<Integer, Direction> m = Maps.newHashMap();
			for (Direction direction : values()) {
				m.put(direction.getValue(), direction);
			}
			map = m;
		}
		{
			Map<Direction, Direction> m = Maps.newEnumMap(Direction.class);
			m.put(TOP, BOTTOM);
			m.put(BOTTOM, TOP);

			m.put(FRONT, BACK);
			m.put(BACK, FRONT);

			m.put(RIGHT, LEFT);
			m.put(LEFT, RIGHT);
			reverseMap = m;
		}
		{
			Map<Direction, Direction> m = Maps.newEnumMap(Direction.class);
			m.put(TOP, FRONT);
			m.put(FRONT, RIGHT);
			m.put(RIGHT, BOTTOM);
			m.put(BOTTOM, BACK);
			m.put(BACK, LEFT);
			m.put(LEFT, TOP);

			nextMap = m;
		}
	}

	public static Iterable<Direction> sortedValues() {
		return nextMap.values();
	}

	public static Direction valueOf(int value) {
		return map.get(value);
	}

	private Direction(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public Direction next() {
		return nextMap.get(this);
	}

	public Direction reverse() {
		return reverseMap.get(this);
	}
}
