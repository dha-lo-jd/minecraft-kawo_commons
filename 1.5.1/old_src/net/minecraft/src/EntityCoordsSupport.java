package net.minecraft.src;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityCoordsSupport {

	private static class FixedDirection {
		final int dX, dY, dZ, dT;

		public FixedDirection(int dX, int dY, int dZ, int dT) {
			super();
			this.dX = dX;
			this.dY = dY;
			this.dZ = dZ;
			this.dT = dT;
		}
	}

	private interface Job {
		boolean _do(Entity entity, double x, double y, double z);
	}

	public static boolean checkSafeAreaAbsolute(Entity entity, double x,
			double y, double z) {
		double w = entity.width / 2;
		return doJobAnd(entity, x, y, z, new Job() {
			@Override
			public boolean _do(Entity entity, double x, double y, double z) {
				return checkSafePoint(entity, x, y, z);
			}
		});
	}

	public static boolean checkSafeAreaRelative(Entity entity, double x,
			double y, double z) {
		double w = entity.width / 2;
		x = x + entity.posX;
		y = y + entity.posY;
		z = z + entity.posZ;
		return doJobAnd(entity, x, y, z, new Job() {
			@Override
			public boolean _do(Entity entity, double x, double y, double z) {
				return checkSafePoint(entity, x, y, z);
			}
		});
	}

	public static boolean checkSafePoint(Entity entity, double x, double y,
			double z) {
		return isNonMaterial(entity, x, y, z)
				&& isNonMaterial(entity, x, y + 1, z)
				&& getMaterial(entity, x, y - 1, z).isSolid();

	}

	public static double getBottomHeightFromGround(Entity entity,
			final boolean ignoreLiquid) {
		return getHeightFromGround(entity, ignoreLiquid,
				entity.boundingBox.minY);
	}

	public static ChunkCoordinates getEntityBlockSide(Entity entity,
			EntityPlayer entityPlayer) {

		MovingObjectPosition movingObjectPosition = getMovingObjectPosition(
				entity, entityPlayer);

		if (movingObjectPosition == null) {
			return new ChunkCoordinates(0, 0, 0);
		}

		int side = movingObjectPosition.sideHit;
		int x = 0;
		int y = 0;
		int z = 0;
		if (side == 0) {
			y--;
		}

		if (side == 1) {
			y++;
		}

		if (side == 2) {
			z--;
		}

		if (side == 3) {
			z++;
		}

		if (side == 4) {
			x--;
		}

		if (side == 5) {
			x++;
		}

		int wX = x;
		int wY = y;
		int wZ = z;

		return new ChunkCoordinates(x, y, z);
	}

	public static int getEntityBlockSideHit(Entity entity,
			EntityPlayer entityPlayer) {

		MovingObjectPosition movingObjectPosition = getMovingObjectPosition(
				entity, entityPlayer);

		if (movingObjectPosition == null) {
			return 0;
		}

		return movingObjectPosition.sideHit;
	}

	public static double getHeightFromGround(Entity entity) {
		return getHeightFromGround(entity, false);
	}

	public static double getHeightFromGround(Entity entity,
			final boolean ignoreLiquid) {
		return getHeightFromGround(entity, ignoreLiquid, entity.posY);
	}

	public static Vec3D getLastTickPosition(Entity entity) {
		return Vec3D.createVectorHelper(entity.lastTickPosX,
				entity.lastTickPosY, entity.lastTickPosZ);
	}

	public static Material getMaterial(Entity entity, double x, double y,
			double z) {
		int x1 = MathHelper.floor_double(x);
		int y1 = MathHelper.floor_double(y);
		int z1 = MathHelper.floor_double(z);
		return entity.worldObj.getBlockMaterial(x1, y1, z1);
	}

	public static Vec3D getPosition(Entity entity) {
		return Vec3D.createVectorHelper(entity.posX, entity.posY, entity.posZ);
	}

	public static Vec3D getPrevPosition(Entity entity) {
		return Vec3D.createVectorHelper(entity.prevPosX, entity.prevPosY,
				entity.prevPosZ);
	}

	public static boolean hasEntityInSpaceAt(World world, int x, int y, int z) {
		{
			Material m = world.getBlockMaterial(x, y - 1, z);
			if (!m.isSolid()) {
				return false;
			}
		}
		for (int i = 0; i < 3; i++) {
			int extX = i - 1;
			for (int j = 0; j < 3; j++) {
				int extY = j;
				for (int k = 0; k < 3; k++) {
					int extZ = k - 1;
					Material m = world.getBlockMaterial(x + extX, y + extY, z
							+ extZ);
					if (m.isSolid()) {
						return false;
					}
				}
			}
		}
		return true;
	}

	public static boolean isNonMaterial(Entity entity, double x, double y,
			double z) {
		return isNonMaterial(entity, x, y, z, false);
	}

	public static boolean isNonMaterial(Entity entity, double x, double y,
			double z, boolean ignoreLiquid) {
		Material m = getMaterial(entity, x, y, z);
		if (m.isSolid() || (!ignoreLiquid && m.isLiquid())) {
			return false;
		}
		return true;
	}

	public static boolean isNonMaterialArea(Entity entity, double x, double y,
			double z) {
		double w = entity.width / 2;
		x = x + entity.posX;
		y = y + entity.posY;
		z = z + entity.posZ;
		return doJobAnd(entity, x, y, z, new Job() {
			@Override
			public boolean _do(Entity entity, double x, double y, double z) {
				return isNonMaterial(entity, x, y, z);
			}
		});

	}

	public static boolean rayTraceEntityBoxToEntityBox(Entity entity,
			Entity targetEntity) {
		return rayTraceEntityToEntity(entity, targetEntity, true)
				&& rayTraceEntityToEntity(entity, targetEntity, false);
	}

	public static boolean rayTraceEntityToEntity(Entity entity,
			Entity targetEntity, boolean bottom) {
		Vec3D vec3dSrc;
		Vec3D vec3dTarget;
		if (bottom) {
			vec3dSrc = Vec3D.createVector(entity.posX, entity.boundingBox.minY,
					entity.posZ);
			vec3dTarget = Vec3D.createVector(targetEntity.posX,
					targetEntity.boundingBox.minY, targetEntity.posZ);
		} else {
			vec3dSrc = Vec3D.createVector(entity.posX, entity.boundingBox.maxY,
					entity.posZ);
			vec3dTarget = Vec3D.createVector(targetEntity.posX,
					targetEntity.boundingBox.maxY, targetEntity.posZ);
		}

		MovingObjectPosition mop = entity.worldObj.rayTraceBlocks(vec3dSrc,
				vec3dTarget);

		return mop != null;
	}

	public static FixedDirection rotateFixedBoxDirection(Entity entity,
			boolean reverse) {
		float vZ;
		float vX;
		float yaw = entity.rotationYaw;
		while (yaw > 360) {
			yaw -= 360;
		}
		while (yaw < 0) {
			yaw += 360;
		}
		if (reverse) {
			vZ = MathHelper.cos(((360 - yaw) / 180F) * (float) Math.PI);
			vX = MathHelper.sin(((360 - yaw) / 180F) * (float) Math.PI);
		} else {
			vZ = MathHelper.cos((yaw / 180F) * (float) Math.PI);
			vX = MathHelper.sin((yaw / 180F) * (float) Math.PI);
		}
		int dX = 0;
		int dZ = 0;
		if (Math.abs(vZ) > Math.abs(vX)) {
			if (vZ >= 0) {
				dZ = 1;
			} else {
				dZ = -1;
			}
		} else {
			if (vX >= 0) {
				dX = -1;
			} else {
				dX = 1;
			}
		}

		float pitch = entity.rotationPitch;
		while (pitch > 360) {
			pitch -= 360;
		}
		while (pitch < 0) {
			pitch += 360;
		}
		float vT = MathHelper.cos((pitch / 180F) * (float) Math.PI);
		float vY = MathHelper.sin((pitch / 180F) * (float) Math.PI);
		int dT = 0;
		int dY = 0;
		if (Math.abs(vT) > Math.abs(vY)) {
			if (vT >= 0) {
				dT = 1;
			} else {
				dT = -1;
			}
		} else {
			if (vY >= 0) {
				dY = -1;
			} else {
				dY = 1;
			}
		}

		return new FixedDirection(dX, dY, dZ, dT);
	}

	public static Vec3D rotateFixedBoxDirection(Entity entity, double x,
			double y, double z) {
		FixedDirection fd = rotateFixedBoxDirection(entity, false);
		return Vec3D.createVector(x * fd.dZ + z * fd.dX * fd.dT + y * fd.dX
				* -fd.dY, y * fd.dT + z * fd.dY, z * fd.dZ * fd.dT + x * -fd.dX
				+ y * fd.dZ * -fd.dY);
	}

	public static ChunkCoordinates rotateFixedBoxDirection(Entity entity,
			int x, int y, int z) {
		return rotateFixedBoxDirection(entity, x, y, z, false);
	}

	public static ChunkCoordinates rotateFixedBoxDirection(Entity entity,
			int x, int y, int z, boolean reverse) {
		FixedDirection fd = rotateFixedBoxDirection(entity, reverse);
		return new ChunkCoordinates(x * fd.dZ + z * fd.dX * fd.dT + y * fd.dX
				* -fd.dY, y * fd.dT + z * fd.dY, z * fd.dZ * fd.dT + x * -fd.dX
				+ y * fd.dZ * -fd.dY);
	}

	public static void updatePositionFromPrev(Entity entity) {
		double newPosX = entity.posX;
		double newPosY = entity.posY;
		double newPosZ = entity.posZ;
		entity.posX = entity.prevPosX;
		entity.posY = entity.prevPosY;
		entity.posZ = entity.prevPosZ;
		entity.setPosition(newPosX, newPosY, newPosZ);
	}

	private static boolean doAreaJobAnd(Entity entity, double fX, int lX,
			double y, double fZ, int lZ, Job job) {
		for (int i = 0; i < lX; i++) {
			for (int j = 0; j < lZ; j++) {
				if (!job._do(entity, fX + lX, y, fZ + lZ)) {
					return false;
				}
			}
		}
		return true;
	}

	private static boolean doAreaJobOr(Entity entity, double fX, int lX,
			double y, double fZ, int lZ, Job job) {
		for (int i = 0; i < lX; i++) {
			for (int j = 0; j < lZ; j++) {
				if (job._do(entity, fX + lX, y, fZ + lZ)) {
					return true;
				}
			}
		}
		return false;
	}

	private static boolean doJobAnd(Entity entity, double x, double y,
			double z, Job job) {
		double w = entity.width / 2;
		return job._do(entity, x, y, z) && job._do(entity, x + w, y, z)
				&& job._do(entity, x, y, z + w)
				&& job._do(entity, x + w, y, z + w)
				&& job._do(entity, x - w, y, z) && job._do(entity, x, y, z - w)
				&& job._do(entity, x - w, y, z - w);
	}

	private static boolean doJobOr(Entity entity, double x, double y, double z,
			Job job) {
		double w = entity.width / 2;
		return job._do(entity, x, y, z) || job._do(entity, x + w, y, z)
				|| job._do(entity, x, y, z + w)
				|| job._do(entity, x + w, y, z + w)
				|| job._do(entity, x - w, y, z) || job._do(entity, x, y, z - w)
				|| job._do(entity, x - w, y, z - w);
	}

	private static MovingObjectPosition getMovingObjectPosition(Entity entity,
			EntityPlayer entityPlayer) {
		Minecraft mc = ModLoader.getMinecraftInstance();

		double d = mc.playerController.getBlockReachDistance();
		Vec3 playerPosV3d = entityPlayer.getPosition(1);
		playerPosV3d = playerPosV3d.addVector(-entity.posX, -entity.posY,
				-entity.posZ);
		playerPosV3d = Vec3.createVectorHelper(playerPosV3d.xCoord,
				playerPosV3d.yCoord, playerPosV3d.zCoord);

		Vec3 playerLookV3d = entityPlayer.getLookVec();
		playerLookV3d = playerPosV3d.addVector(playerLookV3d.xCoord * d,
				playerLookV3d.yCoord * d, playerLookV3d.zCoord * d);
		playerLookV3d = Vec3.createVectorHelper(playerLookV3d.xCoord,
				playerLookV3d.yCoord, playerLookV3d.zCoord);

		float r = ((entity.rotationYaw) * (float) Math.PI) / 180F;

		playerPosV3d.rotateAroundY(r);
		playerPosV3d = playerPosV3d.addVector(0.5, 0.5, 0.5);
		playerLookV3d.rotateAroundY(r);
		playerLookV3d = playerLookV3d.addVector(0.5, 0.5, 0.5);

		MovingObjectPosition movingObjectPosition = Block.stone
				.collisionRayTrace(entity.worldObj, 0, 0, 0, playerPosV3d,
						playerLookV3d);
		return movingObjectPosition;
	}

	protected static double getHeightFromGround(Entity entity,
			final boolean ignoreLiquid, double posY) {
		if (posY < 1) {
			return -1;
		}

		for (int i = 0; i < 256; i++) {
			int y = MathHelper.floor_double(posY) - i - 1;
			double w = entity.width / 2;
			boolean flag = doJobOr(entity, entity.posX, y, entity.posZ,
					new Job() {
						@Override
						public boolean _do(Entity entity, double x, double y,
								double z) {
							return !isNonMaterial(entity, x, y, z, ignoreLiquid);
						}
					});
			if (flag) {
				return posY - MathHelper.floor_double(posY) + i;
			}
		}
		return -1;
	}
}
