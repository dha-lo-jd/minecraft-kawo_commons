package net.minecraft.src;

import java.util.List;
import java.util.Random;

public abstract class EntityHasRenderer extends Entity {
	public int heartsHalvesLife;
	public float field_9365_p;
	public float field_9363_r;
	public float renderYawOffset;
	public float prevRenderYawOffset;

	/** Entity head rotation yaw */
	public float rotationYawHead;

	/** Entity head rotation yaw at previous tick */
	public float prevRotationYawHead;
	protected float field_9362_u;
	protected float field_9361_v;
	protected float field_9360_w;
	protected float field_9359_x;
	protected boolean field_9358_y;

	/** the path for the texture of this entityLiving */
	protected String texture;
	protected boolean field_9355_A;
	protected float field_9353_B;

	/**
	 * a string holding the type of entity it is currently only implemented in
	 * entityPlayer(as 'humanoid')
	 */
	protected String entityType;
	protected float field_9349_D;

	/** The score value of the Mob, the amount of points the mob is worth. */
	protected int scoreValue;
	protected float field_9345_F;

	/**
	 * This gets set on entity death, but never used. Looks like a duplicate of
	 * isDead
	 */
	protected boolean dead;

	/** The experience points the Entity gives. */
	protected int experienceValue;
	public int field_9326_T;
	public float field_9325_U;
	public float field_705_Q;
	public float field_704_R;
	public float field_703_S;

	/** Whether the DataWatcher needs to be updated with the active potions */
	private boolean potionsNeedUpdate;
	private int field_39002_c;

	/**
	 * The number of updates over which the new position and rotation are to be
	 * applied to the entity.
	 */
	protected int newPosRotationIncrements;

	/** The new X position to be applied to the entity. */
	protected double newPosX;

	/** The new Y position to be applied to the entity. */
	protected double newPosY;

	/** The new Z position to be applied to the entity. */
	protected double newPosZ;

	/** The new yaw rotation to be applied to the entity. */
	protected double newRotationYaw;

	/** The new yaw rotation to be applied to the entity. */
	protected double newRotationPitch;
	float field_9348_ae;

	/** intrinsic armor level for entity */
	protected int naturalArmorRating;

	/** Holds the living entity age, used to control the despawn. */
	protected int entityAge;
	protected float moveStrafing;
	protected float moveForward;
	protected float randomYawVelocity;

	/** used to check whether entity is jumping. */
	protected boolean isJumping;
	protected float defaultPitch;
	protected float moveSpeed;

	/** Number of ticks since last jump */
	private int jumpTicks;

	/** This entity's current target. */
	private Entity currentTarget;

	/** How long to keep a specific target entity */
	protected int numTicksToChaseTarget;

	public EntityHasRenderer(World par1World) {
		super(par1World);
		heartsHalvesLife = 20;
		renderYawOffset = 0.0F;
		prevRenderYawOffset = 0.0F;
		rotationYawHead = 0.0F;
		prevRotationYawHead = 0.0F;
		field_9358_y = true;
		texture = "/mob/char.png";
		field_9355_A = true;
		field_9353_B = 0.0F;
		entityType = null;
		field_9349_D = 1.0F;
		scoreValue = 0;
		field_9345_F = 0.0F;
		dead = false;
		field_9326_T = -1;
		field_9325_U = (float) (Math.random() * 0.89999997615814209D + 0.10000000149011612D);
		potionsNeedUpdate = true;
		field_9348_ae = 0.0F;
		naturalArmorRating = 0;
		entityAge = 0;
		isJumping = false;
		defaultPitch = 0.0F;
		moveSpeed = 0.7F;
		jumpTicks = 0;
		numTicksToChaseTarget = 0;
		preventEntitySpawning = true;
		field_9363_r = (float) (Math.random() + 1.0D) * 0.01F;
		setPosition(posX, posY, posZ);
		field_9365_p = (float) Math.random() * 12398F;
		rotationYaw = (float) (Math.random() * Math.PI * 2D);
		rotationYawHead = rotationYaw;
		stepHeight = 0.5F;
	}

	public Random getRNG() {
		return rand;
	}

	public int getAge() {
		return entityAge;
	}

	/**
	 * Sets the head's yaw rotation of the entity.
	 */
	public void setHeadRotationYaw(float par1) {
		rotationYawHead = par1;
	}

	public boolean func_48100_a(Class par1Class) {
		return (net.minecraft.src.EntityCreeper.class) != par1Class
				&& (net.minecraft.src.EntityGhast.class) != par1Class;
	}

	/**
	 * This function applies the benefits of growing back wool and faster
	 * growing up to the acting entity. (This function is used in the
	 * AIEatGrass)
	 */
	public void eatGrassBonus() {
	}

	protected void entityInit() {
		dataWatcher.addObject(8, Integer.valueOf(field_39002_c));
	}

	/**
	 * returns true if the entity provided in the argument can be seen.
	 * (Raytrace)
	 */
	public boolean canEntityBeSeen(Entity par1Entity) {
		return worldObj.rayTraceBlocks(
				Vec3D.createVector(posX, posY + (double) getEyeHeight(), posZ),
				Vec3D.createVector(par1Entity.posX, par1Entity.posY
						+ (double) par1Entity.getEyeHeight(), par1Entity.posZ)) == null;
	}

	/**
	 * Returns the texture's file path as a String.
	 */
	public String getTexture() {
		return texture;
	}

	/**
	 * Returns true if other Entities should be prevented from moving through
	 * this Entity.
	 */
	public boolean canBeCollidedWith() {
		return !isDead;
	}

	/**
	 * Returns true if this entity should push and be pushed by other entities
	 * when colliding.
	 */
	public boolean canBePushed() {
		return !isDead;
	}

	public float getEyeHeight() {
		return height * 0.85F;
	}

	/**
	 * Get number of ticks, at least during which the living entity will be
	 * silent.
	 */
	public int getTalkInterval() {
		return 80;
	}

	/**
	 * Gets called every tick from main Entity class
	 */
	public void onEntityUpdate() {
		super.onEntityUpdate();
		Profiler.startSection("mobBaseTick");

		if (isEntityAlive() && isEntityInsideOpaqueBlock()) {
			if (!attackEntityFrom(DamageSource.inWall, 1))
				;
		}

		if (isImmuneToFire() || worldObj.isRemote) {
			extinguish();
		}

		if (heartsLife > 0) {
			heartsLife--;
		}

		field_9359_x = field_9360_w;
		prevRenderYawOffset = renderYawOffset;
		prevRotationYawHead = rotationYawHead;
		prevRotationYaw = rotationYaw;
		prevRotationPitch = rotationPitch;
		Profiler.endSection();
	}

	/**
	 * Decrements the entity's air supply when underwater
	 */
	protected int decreaseAirSupply(int par1) {
		return par1 - 1;
	}

	/**
	 * Get the experience points the entity currently has.
	 */
	protected int getExperiencePoints(EntityPlayer par1EntityPlayer) {
		return experienceValue;
	}

	/**
	 * Only use is to identify if class is an instance of player for experience
	 * dropping
	 */
	protected boolean isPlayer() {
		return false;
	}

	/**
	 * Spawns an explosion particle around the Entity's location
	 */
	public void spawnExplosionParticle() {
		for (int i = 0; i < 20; i++) {
			double d = rand.nextGaussian() * 0.02D;
			double d1 = rand.nextGaussian() * 0.02D;
			double d2 = rand.nextGaussian() * 0.02D;
			double d3 = 10D;
			worldObj.spawnParticle("explode",
					(posX + (double) (rand.nextFloat() * width * 2.0F))
							- (double) width - d * d3,
					(posY + (double) (rand.nextFloat() * height)) - d1 * d3,
					(posZ + (double) (rand.nextFloat() * width * 2.0F))
							- (double) width - d2 * d3, d, d1, d2);
		}
	}

	/**
	 * Handles updating while being ridden by an entity
	 */
	public void updateRidden() {
		super.updateRidden();
		field_9362_u = field_9361_v;
		field_9361_v = 0.0F;
		fallDistance = 0.0F;
	}

	/**
	 * Sets the position and rotation. Only difference from the other one is no
	 * bounding on the rotation. Args: posX, posY, posZ, yaw, pitch
	 */
	public void setPositionAndRotation2(double par1, double par3, double par5,
			float par7, float par8, int par9) {
		yOffset = 0.0F;
		newPosX = par1;
		newPosY = par3;
		newPosZ = par5;
		newRotationYaw = par7;
		newRotationPitch = par8;
		newPosRotationIncrements = par9;
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	public void onUpdate() {
		super.onUpdate();

		onLivingUpdate();
		double d = posX - prevPosX;
		double d1 = posZ - prevPosZ;
		float f = MathHelper.sqrt_double(d * d + d1 * d1);
		float f1 = renderYawOffset;
		float f2 = 0.0F;
		field_9362_u = field_9361_v;
		float f3 = 0.0F;

		if (f > 0.05F) {
			f3 = 1.0F;
			f2 = f * 3F;
			f1 = ((float) Math.atan2(d1, d) * 180F) / (float) Math.PI - 90F;
		}

		if (!onGround) {
			f3 = 0.0F;
		}

		field_9361_v = field_9361_v + (f3 - field_9361_v) * 0.3F;

		float f4;

		for (f4 = f1 - renderYawOffset; f4 < -180F; f4 += 360F) {
		}

		for (; f4 >= 180F; f4 -= 360F) {
		}

		renderYawOffset += f4 * 0.3F;
		float f5;

		for (f5 = rotationYaw - renderYawOffset; f5 < -180F; f5 += 360F) {
		}

		for (; f5 >= 180F; f5 -= 360F) {
		}

		boolean flag = f5 < -90F || f5 >= 90F;

		if (f5 < -75F) {
			f5 = -75F;
		}

		if (f5 >= 75F) {
			f5 = 75F;
		}

		renderYawOffset = rotationYaw - f5;

		if (f5 * f5 > 2500F) {
			renderYawOffset += f5 * 0.2F;
		}

		if (flag) {
			f2 *= -1F;
		}

		for (; rotationYaw - prevRotationYaw < -180F; prevRotationYaw -= 360F) {
		}

		for (; rotationYaw - prevRotationYaw >= 180F; prevRotationYaw += 360F) {
		}

		for (; renderYawOffset - prevRenderYawOffset < -180F; prevRenderYawOffset -= 360F) {
		}

		for (; renderYawOffset - prevRenderYawOffset >= 180F; prevRenderYawOffset += 360F) {
		}

		for (; rotationPitch - prevRotationPitch < -180F; prevRotationPitch -= 360F) {
		}

		for (; rotationPitch - prevRotationPitch >= 180F; prevRotationPitch += 360F) {
		}

		for (; rotationYawHead - prevRotationYawHead < -180F; prevRotationYawHead -= 360F) {
		}

		for (; rotationYawHead - prevRotationYawHead >= 180F; prevRotationYawHead += 360F) {
		}

		field_9360_w += f2;
	}

	/**
	 * Sets the width and height of the entity. Args: width, height
	 */
	protected void setSize(float par1, float par2) {
		super.setSize(par1, par2);
	}

	public void setMoveForward(float par1) {
		moveForward = par1;
	}

	public void setJumping(boolean par1) {
		isJumping = par1;
	}

	/**
	 * Called frequently so the entity can update its state every tick as
	 * required. For example, zombies and skeletons use this to react to
	 * sunlight and start to burn.
	 */
	public void onLivingUpdate() {
		if (jumpTicks > 0) {
			jumpTicks--;
		}

		if (newPosRotationIncrements > 0) {
			double d = posX + (newPosX - posX)
					/ (double) newPosRotationIncrements;
			double d1 = posY + (newPosY - posY)
					/ (double) newPosRotationIncrements;
			double d2 = posZ + (newPosZ - posZ)
					/ (double) newPosRotationIncrements;
			double d3;

			for (d3 = newRotationYaw - (double) rotationYaw; d3 < -180D; d3 += 360D) {
			}

			for (; d3 >= 180D; d3 -= 360D) {
			}

			rotationYaw += d3 / (double) newPosRotationIncrements;
			rotationPitch += (newRotationPitch - (double) rotationPitch)
					/ (double) newPosRotationIncrements;
			newPosRotationIncrements--;
			setPosition(d, d1, d2);
			setRotation(rotationYaw, rotationPitch);
			List list1 = worldObj.getCollidingBoundingBoxes(this,
					boundingBox.contract(0.03125D, 0.0D, 0.03125D));

			if (list1.size() > 0) {
				double d4 = 0.0D;

				for (int j = 0; j < list1.size(); j++) {
					AxisAlignedBB axisalignedbb = (AxisAlignedBB) list1.get(j);

					if (axisalignedbb.maxY > d4) {
						d4 = axisalignedbb.maxY;
					}
				}

				d1 += d4 - boundingBox.minY;
				setPosition(d, d1, d2);
			}
		}

		Profiler.startSection("ai");
		if (isClientWorld()) {
			Profiler.startSection("oldAi");
			updateEntityActionState();
			Profiler.endSection();
			rotationYawHead = rotationYaw;
		}

		Profiler.endSection();
		boolean flag = isInWater();
		boolean flag1 = handleLavaMovement();

		if (isJumping) {
			if (flag) {
				motionY += 0.039999999105930328D;
			} else if (flag1) {
				motionY += 0.039999999105930328D;
			} else if (onGround && jumpTicks == 0) {
				jump();
				jumpTicks = 10;
			}
		} else {
			jumpTicks = 0;
		}

		moveStrafing *= 0.98F;
		moveForward *= 0.98F;

		field_705_Q = field_704_R;
		double d2 = posX - prevPosX;
		double d3 = posZ - prevPosZ;
		float f4 = MathHelper.sqrt_double(d2 * d2 + d3 * d3) * 4F;

		if (f4 > 1.0F) {
			f4 = 1.0F;
		}

		field_704_R += (f4 - field_704_R) * 0.4F;
		field_703_S += field_704_R;

		randomYawVelocity *= 0.9F;
		Profiler.startSection("push");
		List list = worldObj.getEntitiesWithinAABBExcludingEntity(this,
				boundingBox.expand(0.20000000298023224D, 0.0D,
						0.20000000298023224D));

		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Entity entity = (Entity) list.get(i);

				if (entity.canBePushed()) {
					entity.applyEntityCollision(this);
				}
			}
		}

		Profiler.endSection();
	}

	/**
	 * Returns whether the entity is in a local (client) world
	 */
	protected boolean isClientWorld() {
		return !worldObj.isRemote;
	}

	/**
	 * Causes this entity to do an upwards motion (jumping).
	 */
	protected void jump() {
		motionY = 0.41999998688697815D;

		if (isSprinting()) {
			float f = rotationYaw * 0.01745329F;
			motionX -= MathHelper.sin(f) * 0.2F;
			motionZ += MathHelper.cos(f) * 0.2F;
		}

		isAirBorne = true;
	}

	protected void updateEntityActionState() {
		entityAge++;
		moveStrafing = 0.0F;
		moveForward = 0.0F;
		float f = 8F;

		if (rand.nextFloat() < 0.02F) {
			EntityPlayer entityplayer = worldObj.getClosestPlayerToEntity(this,
					f);

			if (entityplayer != null) {
				currentTarget = entityplayer;
				numTicksToChaseTarget = 10 + rand.nextInt(20);
			} else {
				randomYawVelocity = (rand.nextFloat() - 0.5F) * 20F;
			}
		}

		if (currentTarget != null) {
			faceEntity(currentTarget, 10F, getVerticalFaceSpeed());

			if (numTicksToChaseTarget-- <= 0
					|| currentTarget.isDead
					|| currentTarget.getDistanceSqToEntity(this) > (double) (f * f)) {
				currentTarget = null;
			}
		} else {
			if (rand.nextFloat() < 0.05F) {
				randomYawVelocity = (rand.nextFloat() - 0.5F) * 20F;
			}

			rotationYaw += randomYawVelocity;
			rotationPitch = defaultPitch;
		}

		boolean flag = isInWater();
		boolean flag1 = handleLavaMovement();

		if (flag || flag1) {
			isJumping = rand.nextFloat() < 0.8F;
		}
	}

	/**
	 * The speed it takes to move the entityliving's rotationPitch through the
	 * faceEntity method. This is only currently use in wolves.
	 */
	public int getVerticalFaceSpeed() {
		return 40;
	}

	/**
	 * Changes pitch and yaw so that the entity calling the function is facing
	 * the entity provided as an argument.
	 */
	public void faceEntity(Entity par1Entity, float par2, float par3) {
		double d = par1Entity.posX - posX;
		double d2 = par1Entity.posZ - posZ;
		double d1;

		if (par1Entity instanceof EntityLiving) {
			EntityLiving entityliving = (EntityLiving) par1Entity;
			d1 = (posY + (double) getEyeHeight())
					- (entityliving.posY + (double) entityliving.getEyeHeight());
		} else {
			d1 = (par1Entity.boundingBox.minY + par1Entity.boundingBox.maxY)
					/ 2D - (posY + (double) getEyeHeight());
		}

		double d3 = MathHelper.sqrt_double(d * d + d2 * d2);
		float f = (float) ((Math.atan2(d2, d) * 180D) / Math.PI) - 90F;
		float f1 = (float) (-((Math.atan2(d1, d3) * 180D) / Math.PI));
		rotationPitch = -updateRotation(rotationPitch, f1, par3);
		rotationYaw = updateRotation(rotationYaw, f, par2);
	}

	/**
	 * Arguments: current rotation, intended rotation, max increment.
	 */
	private float updateRotation(float par1, float par2, float par3) {
		float f;

		for (f = par2 - par1; f < -180F; f += 360F) {
		}

		for (; f >= 180F; f -= 360F) {
		}

		if (f > par3) {
			f = par3;
		}

		if (f < -par3) {
			f = -par3;
		}

		return par1 + f;
	}

	/**
	 * interpolated position vector
	 */
	public Vec3D getPosition(float par1) {
		if (par1 == 1.0F) {
			return Vec3D.createVector(posX, posY, posZ);
		} else {
			double d = prevPosX + (posX - prevPosX) * (double) par1;
			double d1 = prevPosY + (posY - prevPosY) * (double) par1;
			double d2 = prevPosZ + (posZ - prevPosZ) * (double) par1;
			return Vec3D.createVector(d, d1, d2);
		}
	}

	/**
	 * returns a (normalized) vector of where this entity is looking
	 */
	public Vec3D getLookVec() {
		return getLook(1.0F);
	}

	/**
	 * interpolated look vector
	 */
	public Vec3D getLook(float par1) {
		if (par1 == 1.0F) {
			float f = MathHelper.cos(-rotationYaw * 0.01745329F
					- (float) Math.PI);
			float f2 = MathHelper.sin(-rotationYaw * 0.01745329F
					- (float) Math.PI);
			float f4 = -MathHelper.cos(-rotationPitch * 0.01745329F);
			float f6 = MathHelper.sin(-rotationPitch * 0.01745329F);
			return Vec3D.createVector(f2 * f4, f6, f * f4);
		} else {
			float f1 = prevRotationPitch + (rotationPitch - prevRotationPitch)
					* par1;
			float f3 = prevRotationYaw + (rotationYaw - prevRotationYaw) * par1;
			float f5 = MathHelper.cos(-f3 * 0.01745329F - (float) Math.PI);
			float f7 = MathHelper.sin(-f3 * 0.01745329F - (float) Math.PI);
			float f8 = -MathHelper.cos(-f1 * 0.01745329F);
			float f9 = MathHelper.sin(-f1 * 0.01745329F);
			return Vec3D.createVector(f7 * f8, f9, f5 * f8);
		}
	}

	/**
	 * Returns render size modifier
	 */
	public float getRenderSizeModifier() {
		return 1.0F;
	}

	/**
	 * Performs a ray trace for the distance specified and using the partial
	 * tick time. Args: distance, partialTickTime
	 */
	public MovingObjectPosition rayTrace(double par1, float par3) {
		Vec3D vec3d = getPosition(par3);
		Vec3D vec3d1 = getLook(par3);
		Vec3D vec3d2 = vec3d.addVector(vec3d1.xCoord * par1, vec3d1.yCoord
				* par1, vec3d1.zCoord * par1);
		return worldObj.rayTraceBlocks(vec3d, vec3d2);
	}

	/**
	 * Will return how many at most can spawn in a chunk at once.
	 */
	public int getMaxSpawnedInChunk() {
		return 4;
	}

	/**
	 * Returns the item that this EntityLiving is holding, if any.
	 */
	public ItemStack getHeldItem() {
		return null;
	}

	/**
	 * Gets the Icon Index of the item currently held
	 */
	public int getItemIcon(ItemStack par1ItemStack, int par2) {
		return par1ItemStack.getIconIndex();
	}

	/**
	 * Renders broken item particles using the given ItemStack
	 */
	public void renderBrokenItemStack(ItemStack par1ItemStack) {
		worldObj.playSoundAtEntity(this, "random.break", 0.8F,
				0.8F + worldObj.rand.nextFloat() * 0.4F);

		for (int i = 0; i < 5; i++) {
			Vec3D vec3d = Vec3D
					.createVectorHelper(
							((double) rand.nextFloat() - 0.5D) * 0.10000000000000001D,
							Math.random() * 0.10000000000000001D + 0.10000000000000001D,
							0.0D);
			vec3d.rotateAroundX((-rotationPitch * (float) Math.PI) / 180F);
			vec3d.rotateAroundY((-rotationYaw * (float) Math.PI) / 180F);
			Vec3D vec3d1 = Vec3D
					.createVectorHelper(
							((double) rand.nextFloat() - 0.5D) * 0.29999999999999999D,
							(double) (-rand.nextFloat()) * 0.59999999999999998D - 0.29999999999999999D,
							0.59999999999999998D);
			vec3d1.rotateAroundX((-rotationPitch * (float) Math.PI) / 180F);
			vec3d1.rotateAroundY((-rotationYaw * (float) Math.PI) / 180F);
			vec3d1 = vec3d1.addVector(posX, posY + (double) getEyeHeight(),
					posZ);
			worldObj.spawnParticle((new StringBuilder()).append("iconcrack_")
					.append(par1ItemStack.getItem().shiftedIndex).toString(),
					vec3d1.xCoord, vec3d1.yCoord, vec3d1.zCoord, vec3d.xCoord,
					vec3d.yCoord + 0.050000000000000003D, vec3d.zCoord);
		}
	}

}
