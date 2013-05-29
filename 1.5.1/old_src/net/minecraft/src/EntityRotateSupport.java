package net.minecraft.src;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;



public class EntityRotateSupport {

	public static boolean fixRotation(Entity entity) {
		boolean flag = false;

		if (entity.rotationYaw > 360) {
			entity.rotationYaw -= 360F;
		}
		if (entity.rotationYaw < 0) {
			entity.rotationYaw += 360F;
		}

		double d = entity.prevRotationYaw - entity.rotationYaw;
		if (d < -180D) {
			entity.prevRotationYaw += 360F;
		}
		if (d >= 180D) {
			entity.prevRotationYaw -= 360F;
		}
		return flag;
	}

	public static void rotateAroundZ(Vec3D v3d, float par1) {
		CoordsSupport.rotateAroundZ(v3d, par1);
	}

	public static void rotateVec3DByEntityRotation(Entity entity, Vec3D v3d) {
		float rotationPitch = entity.rotationPitch;
		float rotationYaw = entity.rotationYaw;
		rotateVec3DByEntityRotation(rotationPitch, rotationYaw, v3d);
	}

	public static void rotateVec3DByEntityRotation(float rotationPitch,
			float rotationYaw, Vec3D v3d) {
		v3d.rotateAroundX(RotateSupport.toRadF(-rotationPitch));
		v3d.rotateAroundY(RotateSupport.toRadF(-rotationYaw));
	}

	public static void rotateVec3DByEntityRotationWithZ(Entity entity, float z,
			Vec3D v3d) {
		float rotationPitch = entity.rotationPitch;
		float rotationYaw = entity.rotationYaw;
		rotateVec3DByEntityRotationWithZ(rotationPitch, rotationYaw, z, v3d);
	}

	public static void rotateVec3DByEntityRotationWithZ(float rotationPitch,
			float rotationYaw, float z, Vec3D v3d) {
		v3d.rotateAroundX(RotateSupport.toRadF(-rotationPitch));
		CoordsSupport.rotateAroundZ(v3d, RotateSupport.toRadF(-z));
		v3d.rotateAroundY(RotateSupport.toRadF(-rotationYaw));
	}

	public static void faceEntity(Entity entity, Entity faceToEntity,
			float par2, float par3) {
		float yaw = getFaceToYaw(entity, faceToEntity);
		float pitch = getFaceToPitch(entity, faceToEntity);
		entity.rotationYaw = RotateSupport.updateRotation(entity.rotationYaw,
				yaw, par2);
		entity.rotationPitch = RotateSupport.updateRotation(
				entity.rotationPitch, pitch, par3);
	}

	public static void faceXYZ(Entity entity, Vec3D v3d, float par2, float par3) {
		float yaw = getFaceToYaw(entity, v3d);
		float pitch = getFaceToPitch(entity, v3d);
		entity.rotationYaw = RotateSupport.updateRotation(entity.rotationYaw,
				yaw, par2);
		entity.rotationPitch = RotateSupport.updateRotation(
				entity.rotationPitch, pitch, par3);
	}

	public static float getFaceToPitch(Entity entity, Vec3D v3d) {
		return getFaceToPitch(entity, v3d.xCoord, v3d.yCoord, v3d.zCoord);
	}

	public static float getFaceToYaw(Entity entity, Vec3D v3d) {
		return getFaceToYaw(entity, v3d.xCoord, v3d.zCoord);
	}

	public static float getFaceToPitch(Entity entity, double x, double y,
			double z) {
		return RotateSupport.getFaceToPitch(entity.posX,
				(entity.posY + (double) entity.getEyeHeight()), entity.posZ, x,
				y, z);
	}

	public static float getFaceToYaw(Entity entity, double x, double z) {
		return RotateSupport.getFaceToYaw(Vec3D.createVector(entity.posX,
				(entity.posY + (double) entity.getEyeHeight()), entity.posZ),
				x, z);
	}

	public static float getFaceToPitch(Entity entity, Entity faceToEntity) {
		double d1;

		if (faceToEntity instanceof EntityLiving) {
			EntityLiving entityliving = (EntityLiving) faceToEntity;
			d1 = (entityliving.posY + (double) entityliving.getEyeHeight());
		} else {
			d1 = (faceToEntity.boundingBox.minY + faceToEntity.boundingBox.maxY) / 2D;
		}
		return getFaceToPitch(entity, faceToEntity.posX, d1, faceToEntity.posZ);
	}

	public static float getFaceToYaw(Entity entity, Entity faceToEntity) {
		return getFaceToYaw(entity, faceToEntity.posX, faceToEntity.posZ);
	}

}
