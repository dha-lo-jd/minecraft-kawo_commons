package net.minecraft.src;

import java.util.Set;

public class ReversibleN1Map<T, K> implements IReversibleMap<T, K> {
	private IReversibleMap<T, K> map = new ReversibleMap<T, K>();

	@Override
	public void put(T t, K k) {
		if (containB(k)) {
			removeB(k);
		}
		map.put(t, k);
	}

	@Override
	public boolean containA(T t) {
		return map.containA(t);
	}

	@Override
	public boolean containB(K k) {
		return map.containB(k);
	}

	@Override
	public Set<K> getA(T t) {
		return map.getA(t);
	}

	@Override
	public Set<T> getB(K k) {
		return map.getB(k);
	}

	public T getSingleB(K k) {
		if (containB(k)) {
			Set<T> s = map.getB(k);
			if (s == null) {
				return null;
			}
			for (T t : s) {
				return t;
			}
		}
		return null;
	}

	@Override
	public void removeA(T t) {
		map.removeA(t);
	}

	@Override
	public void removeTFromB(K k, T t) {
		map.removeTFromB(k, t);
	}

	@Override
	public void removeB(K k) {
		map.removeB(k);
	}

	@Override
	public void removeKFromA(T t, K k) {
		map.removeKFromA(t, k);
	}

	@Override
	public Set<K> getAllB() {
		return map.getAllB();
	}

	@Override
	public Set<T> getAllA() {
		return map.getAllA();
	}

}
