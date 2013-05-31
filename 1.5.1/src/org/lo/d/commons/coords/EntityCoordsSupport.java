package org.lo.d.commons.coords;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

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
		boolean _do(Entity entity, NumPoint3D<?, ?> point3d);
	}

	public static boolean checkSafeAreaAbsolute(Entity entity, NumPoint3D<?, ?> point3d) {
		final int height = MathHelper.floor_float(entity.height);
		return doJobAnd(entity, point3d, new Job() {
			@Override
			public boolean _do(Entity entity, NumPoint3D<?, ?> point3d) {
				return checkSafePoint(entity, point3d, height);
			}
		});
	}

	public static boolean checkSafeAreaRelative(Entity entity, NumPoint3D<?, ?> point3d) {
		return checkSafeAreaAbsolute(entity, point3d.addPoint(new EntityPoint3DDouble(entity)));
	}

	public static boolean checkSafePoint(Entity entity, NumPoint3D<?, ?> point3d, int height) {
		if (!isNonMaterial(entity, point3d)) {
			return false;
		}
		for (int i = 1; i <= height; i++) {
			if (!isNonMaterial(entity, point3d.addY(i))) {
				return false;
			}
		}
		return getMaterial(entity, point3d.add(Direction.BOTTOM)).isSolid();

	}

	public static double getBottomHeightFromGround(Entity entity,
			final boolean ignoreLiquid) {
		return getHeightFromGround(entity, ignoreLiquid,
				entity.boundingBox.minY);
	}

	public static Point3D getEntityBlockSide(Entity entity,
			EntityPlayer entityPlayer) {

		MovingObjectPosition movingObjectPosition = getMovingObjectPosition(
				entity, entityPlayer);

		if (movingObjectPosition == null) {
			return new Point3D(0, 0, 0);
		}

		int side = movingObjectPosition.sideHit;
		Direction direction = Direction.valueOf(side);
		return Point3D.map.get(direction);
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

	public static Vec3 getLastTickPosition(Entity entity) {
		return Vec3.createVectorHelper(entity.lastTickPosX,
				entity.lastTickPosY, entity.lastTickPosZ);
	}

	public static Material getMaterial(Entity entity, NumPoint3D<?, ?> point3d) {
		return getMaterial(entity, new Point3D(point3d));
	}

	public static Material getMaterial(Entity entity, Point3D point3d) {
		return entity.worldObj.getBlockMaterial(point3d.getX(), point3d.getY(), point3d.getZ());
	}

	public static Vec3 getPosition(Entity entity) {
		return Vec3.createVectorHelper(entity.posX, entity.posY, entity.posZ);
	}

	public static Vec3 getPrevPosition(Entity entity) {
		return Vec3.createVectorHelper(entity.prevPosX, entity.prevPosY,
				entity.prevPosZ);
	}

	public static boolean isNonMaterial(Entity entity, NumPoint3D<?, ?> point3d) {
		return isNonMaterial(entity, point3d, false);
	}

	public static boolean isNonMaterial(Entity entity, NumPoint3D<?, ?> point3d, boolean ignoreLiquid) {
		Material m = getMaterial(entity, point3d);
		if (m.isSolid() || (!ignoreLiquid && m.isLiquid())) {
			return false;
		}
		return true;
	}

	public static boolean isNonMaterialArea(Entity entity, NumPoint3D<?, ?> point3d) {
		return doJobAnd(entity, point3d, new Job() {
			@Override
			public boolean _do(Entity entity, NumPoint3D<?, ?> point3d) {
				return isNonMaterial(entity, point3d);
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
		Vec3 vec3dSrc;
		Vec3 vec3dTarget;
		if (bottom) {
			vec3dSrc = entity.worldObj.getWorldVec3Pool().getVecFromPool(entity.posX, entity.boundingBox.minY,
					entity.posZ);
			vec3dTarget = entity.worldObj.getWorldVec3Pool().getVecFromPool(targetEntity.posX,
					targetEntity.boundingBox.minY, targetEntity.posZ);
		} else {
			vec3dSrc = entity.worldObj.getWorldVec3Pool().getVecFromPool(entity.posX, entity.boundingBox.maxY,
					entity.posZ);
			vec3dTarget = entity.worldObj.getWorldVec3Pool().getVecFromPool(targetEntity.posX,
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

	public static Vec3 rotateFixedBoxDirection(Entity entity, double x,
			double y, double z) {
		FixedDirection fd = rotateFixedBoxDirection(entity, false);
		return entity.worldObj.getWorldVec3Pool().getVecFromPool(x * fd.dZ + z * fd.dX * fd.dT + y * fd.dX
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

	private static boolean doJob(Entity entity, NumPoint3D<?, ?> point3d, Job job, boolean isAnd) {
		double w = entity.width / 2;
		int xMin = MathHelper.floor_double(point3d.getX().doubleValue() - w);
		int xMax = MathHelper.floor_double(point3d.getX().doubleValue() + w);
		int zMin = MathHelper.floor_double(point3d.getZ().doubleValue() - w);
		int zMax = MathHelper.floor_double(point3d.getZ().doubleValue() + w);
		for (int ix = xMin; ix <= xMax; ix++) {
			for (int iz = zMin; iz <= zMax; iz++) {
				if (job._do(entity, new Point3DDouble((double) ix, point3d.getY().doubleValue(), (double) iz)) != isAnd) {
					return !isAnd;
				}
			}
		}
		return isAnd;
	}

	private static boolean doJobAnd(Entity entity, NumPoint3D<?, ?> point3d, Job job) {
		return doJob(entity, point3d, job, true);
	}

	private static boolean doJobOr(Entity entity, NumPoint3D<?, ?> point3d, Job job) {
		return doJob(entity, point3d, job, false);
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

		EntityPoint3DDouble ep = new EntityPoint3DDouble(entity);
		Point3DDouble p = new Point3DDouble(ep.point2d, (double) MathHelper.floor_double(posY));
		p = p.addY((double) -1);
		for (int i = 0; i < 256; i++) {
			boolean flag = doJobOr(entity, p.addY((double) -1),
					new Job() {
						@Override
						public boolean _do(Entity entity, NumPoint3D<?, ?> point3d) {
							return !isNonMaterial(entity, point3d, ignoreLiquid);
						}
					});
			if (flag) {
				return posY - MathHelper.floor_double(posY) + i;
			}
		}
		return -1;
	}
}
