package net.minecraft.src;


public class MaidExSupport {

	public static void setMaidHomeXYZ(EntityLittleMaid entityMaid, int x,
			int y, int z) {
		double rx = entityMaid.posX;
		double ry = entityMaid.posY;
		double rz = entityMaid.posZ;

		entityMaid.posX = x;
		entityMaid.posY = y;
		entityMaid.posZ = z;

		entityMaid.setHomePosition();

		entityMaid.posX = rx;
		entityMaid.posY = ry;
		entityMaid.posZ = rz;
	}

}
