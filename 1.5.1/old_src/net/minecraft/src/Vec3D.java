package net.minecraft.src;

import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3Pool;

public class Vec3D extends Vec3 {

	public static Vec3D createVector(double par0, double par2, double par4)
	{
		return new Vec3D(fakePool, par0, par2, par4);
	}

	public static Vec3D createVectorHelper(double par0, double par2, double par4)
	{
		return new Vec3D(fakePool, par0, par2, par4);
	}

	public Vec3D(Vec3Pool par1Vec3Pool, double par2, double par4, double par6) {
		super(par1Vec3Pool, par2, par4, par6);
	}
}
