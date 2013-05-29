package net.minecraft.src;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;



public class GenLayerLand extends GenLayer {
	public GenLayerLand(long par1) {
		super(par1);
	}

	/**
	 * Returns a list of integer values generated by this layer. These may be
	 * interpreted as temperatures, rainfall amounts, or biomeList[] indices
	 * based on the particular GenLayer subclass.
	 */
	public int[] getInts(int par1, int par2, int par3, int par4) {
		int ai[] = IntCache.getIntCache(par3 * par4);

		for (int i = 0; i < ai.length; i++) {
			ai[i] = 1;
		}

		return ai;
	}
}