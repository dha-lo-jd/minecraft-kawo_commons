package net.minecraft.src;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.block.material.Material;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class CoordsSupport {

	public static Vec3D copyVectorHelper(Vec3D v3d) {
		return Vec3D.createVectorHelper(v3d.xCoord, v3d.yCoord, v3d.zCoord);
	}

	public static Set<ChunkCoordinates> getCoordArea2D(double minX,
			double minZ, double maxX, double maxZ, double y) {
		return getCoordArea2D(MathHelper.floor_double(minX),
				MathHelper.floor_double(minZ), MathHelper.floor_double(maxX),
				MathHelper.floor_double(maxZ), MathHelper.floor_double(y));
	}

	public static Vec3D[] getBezier2D(double startX, double startZ,
			double startVectorX, double startVectorZ, double endX, double endZ,
			double endVectorX, double endVectorZ, int vertexFactor) {
		Vec3D startPoint = Vec3D.createVector(startX, 0, startZ);
		Vec3D startVector = Vec3D.createVector(startX + startVectorX, 0, startZ
				+ startVectorZ);
		Vec3D endPoint = Vec3D.createVector(endX, 0, endZ);
		Vec3D endVector = Vec3D.createVector(endX + endVectorX, 0, endZ
				+ endVectorZ);
		return getBezier2D(startPoint, startVector, endPoint, endVector,
				vertexFactor);
	}

	public static Vec3D[] getBezier2D(Vec3D startPoint, Vec3D startVector,
			Vec3D endPoint, Vec3D endVector, int vertexFactor) {
		Vec3D[] points = new Vec3D[vertexFactor];
		for (int i = 0; i < vertexFactor; i++) {
			double rate = i / (double) vertexFactor;
			double x = startPoint.xCoord * (1 - rate) * (1 - rate) * (1 - rate)
					+ 3 * startVector.xCoord * rate * (1 - rate) * (1 - rate)
					+ 3 * endVector.xCoord * rate * rate * (1 - rate)
					+ endPoint.xCoord * rate * rate * rate;

			double z = startPoint.zCoord * (1 - rate) * (1 - rate) * (1 - rate)
					+ 3 * startVector.zCoord * rate * (1 - rate) * (1 - rate)
					+ 3 * endVector.zCoord * rate * rate * (1 - rate)
					+ endPoint.zCoord * rate * rate * rate;

			Vec3D v3d = Vec3D.createVector(x, 0, z);
			points[i] = v3d;
		}
		return points;
	}

	public static Set<ChunkCoordinates> getCoordArea2D(int minX, int minZ,
			int maxX, int maxZ, int y) {
		Set<ChunkCoordinates> result = new HashSet<ChunkCoordinates>();
		if (maxX < minX) {
			int m = minX;
			minX = maxX;
			maxX = m;
		}
		if (maxZ < minZ) {
			int m = minZ;
			minZ = maxZ;
			maxZ = m;
		}
		for (int i = minX; i <= maxX; i++) {
			for (int j = minZ; j <= maxZ; j++) {
				result.add(new ChunkCoordinates(i, y, j));
			}
		}
		return result;
	}

	public static MovingObjectPosition getMovingObjectPosition(World world,
			Vec3D eyePos, Vec3D lookPos, float dist) {
		// Vec3D vec3d = Vec3D.createVector(lookPos.xCoord - eyePos.xCoord,
		// lookPos.yCoord - eyePos.yCoord, lookPos.zCoord - eyePos.zCoord)
		// .normalize();
		Vec3D vec3d = Vec3D.createVector(lookPos.xCoord * dist, lookPos.yCoord
				* dist, lookPos.zCoord * dist);
		return world.rayTraceBlocks(eyePos, eyePos.addVector(lookPos.xCoord
				* dist, lookPos.yCoord * dist, lookPos.zCoord * dist));
	}

	public static Set<ChunkCoordinates> getRotateCoordArea2D(
			Set<ChunkCoordinates> set, Vec3D rotationPos, float yaw) {
		Set<ChunkCoordinates> result = new HashSet<ChunkCoordinates>();
		for (ChunkCoordinates chunkCoordinates : set) {
			result.add(rotateCoords(chunkCoordinates, rotationPos, yaw));
		}
		return result;
	}

	public static boolean isNonMaterial(World world, ChunkCoordinates pos,
			boolean ignoreLiquid) {
		return isNonMaterial(world, pos.posX, pos.posY, pos.posZ, ignoreLiquid);
	}

	public static boolean isNonMaterial(World world, int x, int y, int z,
			boolean ignoreLiquid) {
		Material m = world.getBlockMaterial(x, y, z);
		if (m.isSolid() || (!ignoreLiquid && m.isLiquid())) {
			return false;
		}
		return true;
	}

	public static boolean isNonMaterial(World world, int x, int y, int z,
			Set<Material> ignoreMaterials) {
		Material m = world.getBlockMaterial(x, y, z);
		if (m.isSolid() && !ignoreMaterials.contains(m)) {
			return false;
		}
		return true;
	}

	public static void rotateAroundZ(Vec3D v3d, float par1) {
		float f = MathHelper.cos(par1);
		float f1 = MathHelper.sin(par1);
		double d = v3d.xCoord * (double) f - v3d.yCoord * (double) f1;
		double d1 = v3d.yCoord * (double) f + v3d.xCoord * (double) f1;
		double d2 = v3d.zCoord;
		v3d.xCoord = d;
		v3d.yCoord = d1;
		v3d.zCoord = d2;
	}

	public static ChunkCoordinates toChunkCoordinates(Vec3D v3d) {
		return new ChunkCoordinates(MathHelper.floor_double(v3d.xCoord),
				MathHelper.floor_double(v3d.yCoord),
				MathHelper.floor_double(v3d.zCoord));
	}

	public static Vec3D toVec3D(ChunkCoordinates cp) {
		return Vec3D.createVectorHelper(cp.posX, cp.posY, cp.posZ);
	}

	private static ChunkCoordinates rotateCoords(ChunkCoordinates coords,
			Vec3D rotationPos, float yaw) {

		float f = MathHelper.cos(yaw);
		float f1 = MathHelper.sin(yaw);
		double x = coords.posX - rotationPos.xCoord;
		double z = coords.posZ - rotationPos.zCoord;
		double d = x * (double) f + z * (double) f1;
		double d2 = z * (double) f - x * (double) f1;

		return new ChunkCoordinates(MathHelper.floor_double(d
				+ rotationPos.xCoord), coords.posY, MathHelper.floor_double(d2
				+ rotationPos.zCoord));
	}

}
