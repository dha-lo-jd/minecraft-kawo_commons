package net.minecraft.src;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ReversibleMap<T, K> implements IReversibleMap<T, K> {
	private Map<T, Set<K>> map1 = new HashMap<T, Set<K>>();
	private Map<K, Set<T>> map2 = new HashMap<K, Set<T>>();

	/* (非 Javadoc)
	 * @see net.minecraft.src.IReversibleMap#put(T, K)
	 */
	@Override
	public void put(T t, K k) {
		if (!map1.containsKey(t)) {
			map1.put(t, new HashSet<K>());
		}
		map1.get(t).add(k);
		if (!map2.containsKey(k)) {
			map2.put(k, new HashSet<T>());
		}
		map2.get(k).add(t);
	}

	/* (非 Javadoc)
	 * @see net.minecraft.src.IReversibleMap#containA(T)
	 */
	@Override
	public boolean containA(T t) {
		return map1.containsKey(t);
	}

	/* (非 Javadoc)
	 * @see net.minecraft.src.IReversibleMap#containB(K)
	 */
	@Override
	public boolean containB(K k) {
		return map2.containsKey(k);
	}

	/* (非 Javadoc)
	 * @see net.minecraft.src.IReversibleMap#getA(T)
	 */
	@Override
	public Set<K> getA(T t) {
		return map1.get(t);
	}

	/* (非 Javadoc)
	 * @see net.minecraft.src.IReversibleMap#getB(K)
	 */
	@Override
	public Set<T> getB(K k) {
		return map2.get(k);
	}

	/* (非 Javadoc)
	 * @see net.minecraft.src.IReversibleMap#removeA(T)
	 */
	@Override
	public void removeA(T t) {
		if (map1.containsKey(t)) {
			for (K k : getA(t)) {
				removeTFromB(k, t);
			}
			map1.remove(t);
		}
	}

	/* (非 Javadoc)
	 * @see net.minecraft.src.IReversibleMap#removeTFromB(K, T)
	 */
	@Override
	public void removeTFromB(K k, T t) {
		if (map2.containsKey(k)) {
			map2.get(k).remove(t);
		}
	}

	/* (非 Javadoc)
	 * @see net.minecraft.src.IReversibleMap#removeB(K)
	 */
	@Override
	public void removeB(K k) {
		if (map2.containsKey(k)) {
			for (T t : getB(k)) {
				removeKFromA(t, k);
			}
			map2.remove(k);
		}
	}

	/* (非 Javadoc)
	 * @see net.minecraft.src.IReversibleMap#removeKFromA(T, K)
	 */
	@Override
	public void removeKFromA(T t, K k) {
		if (map1.containsKey(t)) {
			map1.get(t).remove(k);
		}
	}

	/* (非 Javadoc)
	 * @see net.minecraft.src.IReversibleMap#getAllB()
	 */
	@Override
	public Set<K> getAllB() {
		Set<K> result = new HashSet<K>();
		for (Set<K> set : map1.values()) {
			result.addAll(set);
		}
		return result;
	}

	/* (非 Javadoc)
	 * @see net.minecraft.src.IReversibleMap#getAllA()
	 */
	@Override
	public Set<T> getAllA() {
		Set<T> result = new HashSet<T>();
		for (Set<T> set : map2.values()) {
			result.addAll(set);
		}
		return result;
	}
}
