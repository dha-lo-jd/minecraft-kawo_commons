package net.minecraft.src;

import java.util.Set;

public interface IReversibleMap<T, K> {

	public abstract void put(T t, K k);

	public abstract boolean containA(T t);

	public abstract boolean containB(K k);

	public abstract Set<K> getA(T t);

	public abstract Set<T> getB(K k);

	public abstract void removeA(T t);

	public abstract void removeTFromB(K k, T t);

	public abstract void removeB(K k);

	public abstract void removeKFromA(T t, K k);

	public abstract Set<K> getAllB();

	public abstract Set<T> getAllA();

}