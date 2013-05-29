package net.minecraft.src;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;



public class EntityPosition {
	private double posX;
	private double posY;
	private double posZ;

	private float rotationYaw;

	public double getPosX() {
		return posX;
	}

	public void setPosX(double posX) {
		this.posX = posX;
	}

	public double getPosY() {
		return posY;
	}

	public void setPosY(double posY) {
		this.posY = posY;
	}

	public double getPosZ() {
		return posZ;
	}

	public void setPosZ(double posZ) {
		this.posZ = posZ;
	}

	public float getRotationYaw() {
		return rotationYaw;
	}

	public void setRotationYaw(float rotationYaw) {
		this.rotationYaw = rotationYaw;
	}

	public float getRotationPitch() {
		return rotationPitch;
	}

	public void setRotationPitch(float rotationPitch) {
		this.rotationPitch = rotationPitch;
	}

	private float rotationPitch;

	public EntityPosition(Entity entity) {
		this(entity.posX, entity.posY, entity.posZ, entity.rotationYaw,
				entity.rotationPitch);
	}

	private EntityPosition() {
	}

	public EntityPosition(double posX, double posY, double posZ) {
		this(posX, posY, posZ, 0, 0);
	}

	public EntityPosition(double posX, double posY, double posZ,
			float rotationYaw, float rotationPitch) {
		super();
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		this.rotationYaw = rotationYaw;
		this.rotationPitch = rotationPitch;
	}

	public ChunkCoordinates getChunkCoordinates() {
		return new ChunkCoordinates(MathHelper.floor_double(posX),
				MathHelper.floor_double(posY), MathHelper.floor_double(posZ));
	}

	public void setPositon(ChunkCoordinates cp) {
		this.posX = cp.posX;
		this.posY = cp.posY;
		this.posZ = cp.posZ;

	}

	public void setPositonToEntity(Entity entity) {
		entity.setPosition(posX, posY, posZ);
	}

	public void setPositonAndRotationToEntity(Entity entity) {
		setPositonToEntity(entity);
		setRotationToEntity(entity);
	}

	public void setRotationToEntity(Entity entity) {
		entity.prevRotationYaw = entity.rotationYaw = rotationYaw;
		entity.prevRotationPitch = entity.rotationPitch = rotationPitch;
		double d = entity.prevRotationYaw - rotationYaw;

		if (d < -180D) {
			entity.prevRotationYaw += 360F;
		}

		if (d >= 180D) {
			entity.prevRotationYaw -= 360F;
		}
		entity.setRotation(rotationYaw, rotationPitch);
	}

	public void setLocationAndAnglesToEntity(Entity entity) {
		entity.setLocationAndAngles(posX, posY, posZ, rotationYaw,
				rotationPitch);
	}

	public static void syncPrevPositionAndRotation(Entity srcEntity,
			Entity destEntity) {
		syncPrevPosition(srcEntity, destEntity);
		syncPrevRotation(srcEntity, destEntity);
	}

	public static void syncPrevRotation(Entity srcEntity, Entity destEntity) {
		destEntity.prevRotationYaw = srcEntity.prevRotationYaw;
		destEntity.prevRotationPitch = srcEntity.prevRotationPitch;
	}

	public static void syncPrevPosition(Entity srcEntity, Entity destEntity) {
		destEntity.lastTickPosX = srcEntity.lastTickPosX;
		destEntity.lastTickPosY = srcEntity.lastTickPosY;
		destEntity.lastTickPosZ = srcEntity.lastTickPosZ;
		destEntity.prevPosX = srcEntity.prevPosX;
		destEntity.prevPosY = srcEntity.prevPosY;
		destEntity.prevPosZ = srcEntity.prevPosZ;
	}

	public static void setPositonToEntityFromChunkCoordinates(Entity entity,
			ChunkCoordinates cp) {
		entity.setPosition(cp.posX, cp.posY, cp.posZ);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[").append(posX).append(",").append(posY).append(",")
				.append(posZ).append("]");
		sb.append("[").append(rotationPitch).append(",").append(rotationYaw)
				.append("]");
		sb.append(super.toString());
		return sb.toString();
	}

	public static EntityPosition getInstanceFromNBTTag(String tagName,
			NBTTagCompound nbttagcompound) {
		EntityPosition pos = null;
		if (nbttagcompound.hasKey(tagName)) {
			NBTTagCompound tagCompound = nbttagcompound.getCompoundTag(tagName);
			pos = new EntityPosition();
			pos.readFromNBT(tagCompound);
		}
		return pos;
	}

	public void setInstanceToNBTTag(String tagName,
			NBTTagCompound nbttagcompound) {
		nbttagcompound.setCompoundTag(tagName,
				this.writeToNBT(new NBTTagCompound()));
	}

	public NBTTagCompound readFromNBT(NBTTagCompound nbttagcompound) {
		posX = nbttagcompound.getDouble("posX");
		posY = nbttagcompound.getDouble("posY");
		posZ = nbttagcompound.getDouble("posZ");

		rotationYaw = nbttagcompound.getFloat("rotationYaw");
		rotationPitch = nbttagcompound.getFloat("rotationPitch");

		return nbttagcompound;
	}

	public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound) {
		nbttagcompound.setDouble("posX", posX);
		nbttagcompound.setDouble("posY", posY);
		nbttagcompound.setDouble("posZ", posZ);

		nbttagcompound.setFloat("rotationYaw", rotationYaw);
		nbttagcompound.setFloat("rotationPitch", rotationPitch);

		return nbttagcompound;
	}

}
