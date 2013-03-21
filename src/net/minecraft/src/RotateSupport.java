package net.minecraft.src;

public class RotateSupport {

	public static float getFaceToPitch(double posX, double posY, double posZ,
			double lookX, double lookY, double lookZ) {
		double d = lookX - posX;
		double d2 = lookZ - posZ;
		double d1 = lookY - posY;

		double d3 = MathHelper.sqrt_double(d * d + d2 * d2);
		float f1 = (float) (((Math.atan2(d1, d3) * 180D) / Math.PI));
		float pitch = f1;

		while (pitch > 180) {
			pitch -= 360;
		}
		while (pitch < -180) {
			pitch += 360;
		}
		return pitch;
	}

	public static float getFaceToPitch(Vec3D pos, double x, double y, double z) {
		return getFaceToPitch(pos.xCoord, pos.yCoord, pos.zCoord, x, y, z);
	}

	public static float getFaceToPitch(Vec3D pos, Vec3D look) {
		return getFaceToPitch(pos, look.xCoord, look.yCoord, look.zCoord);
	}

	public static float getFaceToYaw(double posX, double posZ, double lookX,
			double lookZ) {
		double d = lookX - posX;
		double d2 = lookZ - posZ;

		float f = (float) ((Math.atan2(d2, d) * 180D) / Math.PI) + 90F;
		float yaw = f;
		while (yaw > 180) {
			yaw -= 360;
		}
		while (yaw < -180) {
			yaw += 360;
		}
		return yaw;
	}

	public static float getFaceToYaw(Vec3D pos, double x, double z) {
		return getFaceToYaw(pos.xCoord, pos.zCoord, x, z);
	}

	public static float getFaceToYaw(Vec3D pos, Vec3D look) {
		return getFaceToYaw(pos, look.xCoord, look.zCoord);
	}

	public static float toRadF(float angleDeg) {
		return (angleDeg * (float) Math.PI) / 180F;
	}

	public static float updateDirectionalRotation(float par1, float par2,
			float par3, boolean minus) {
		float f;

		for (f = par2 - par1; f < -180F; f += 360F) {
		}

		for (; f >= 180F; f -= 360F) {
		}

		if (!minus) {
			if (f > par3 || f < 0) {
				f = par3;
			}

		} else {
			if (f < -par3 || f > 0) {
				f = -par3;
			}
		}

		return par1 + f;
	}

	public static float updateRotation(float src, float dist, float rate) {
		float f;

		for (f = dist - src; f < -180F; f += 360F) {
		}

		for (; f >= 180F; f -= 360F) {
		}

		if (f > rate) {
			f = rate;
		}

		if (f < -rate) {
			f = -rate;
		}

		return src + f;
	}

	public static float fixAngle(float angle, float offset) {

		float cMax = 360F - offset;
		float cMin = 0.0F - offset;

		while (angle >= cMax) {
			angle -= 360F;
		}
		while (angle < cMin) {
			angle += 360F;
		}

		return angle;
	}

	public static float fixAngle(float angle, float min, float max, float offset) {

		angle = fixAngle(angle, offset);
		if (angle > max) {
			angle = max;
		}
		if (angle < min) {
			angle = min;
		}

		return angle;
	}
}
