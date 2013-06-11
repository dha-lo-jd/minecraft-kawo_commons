package org.lo.d.commons.renderer;

import org.lo.d.commons.coords.NumPoint3D;
import org.lwjgl.opengl.GL11;

public class Point3DRenderSupport {

	public static void glTranslatef(NumPoint3D<?, ?> point3d) {
		GL11.glTranslatef(point3d.getX().floatValue(), point3d.getY().floatValue(), point3d.getZ().floatValue());
	}
}
