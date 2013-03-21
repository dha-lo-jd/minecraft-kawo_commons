package net.minecraft.src;

public abstract class EntityLittleMaidExProxy extends EntityLittleMaid {
	public EntityLittleMaidExProxy(World world) {
		super(world);
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, int i) {
		return attackEntityFromSuper(damagesource, i);
	}

	public boolean attackEntityFromSuper(DamageSource damagesource, int i) {
		return do_attackEntityFromSuper(damagesource, i);
	}

	@Override
	public boolean canAttackWithItem() {
		return canAttackWithItemSuper();
	}

	public boolean canAttackWithItemSuper() {
		return do_canAttackWithItemSuper();
	}

	@Override
	public boolean canBeCollidedWith() {
		return canBeCollidedWithSuper();
	}

	public boolean canBeCollidedWithSuper() {
		return do_canBeCollidedWithSuper();
	}

	@Override
	public boolean canEntityItemBeSeen(Entity entity) {
		return canEntityItemBeSeenSuper(entity);
	}

	public boolean canEntityItemBeSeenSuper(Entity entity) {
		return do_canEntityItemBeSeenSuper(entity);
	}

	@Override
	public boolean canTriggerWalking() {
		return canTriggerWalkingSuper();
	}

	public boolean canTriggerWalkingSuper() {
		return do_canTriggerWalkingSuper();
	}

	@Override
	public void destroyCurrentEquippedItem() {
		destroyCurrentEquippedItemSuper();
	}

	public void destroyCurrentEquippedItemSuper() {
		do_destroyCurrentEquippedItemSuper();
	}

	public boolean do_attackEntityFromSuper(DamageSource damagesource, int i) {
		return super.attackEntityFrom(damagesource, i);
	}

	public boolean do_canAttackWithItemSuper() {
		return super.canAttackWithItem();
	}

	public boolean do_canBeCollidedWithSuper() {
		return super.canBeCollidedWith();
	}

	public boolean do_canEntityItemBeSeenSuper(Entity entity) {
		return super.canEntityItemBeSeen(entity);
	}

	public boolean do_canTriggerWalkingSuper() {
		return super.canTriggerWalking();
	}

	public void do_destroyCurrentEquippedItemSuper() {
		super.destroyCurrentEquippedItem();
	}

	public void do_eatSugarSuper(boolean flag, boolean flag1) {
		super.eatSugar(flag, flag1);
	}

	public void do_faceEntitySuper(Entity entity, float f, float f1) {
		super.faceEntity(entity, f, f1);
	}

	public void do_faceXYZSuper(int i, int j, int k, float f, float f1) {
		super.faceXYZ(i, j, k, f, f1);
	}

	public float do_getBlockPathWeightSuper(int i, int j, int k) {
		return super.getBlockPathWeight(i, j, k);
	}

	public boolean do_getCanSpawnHereSuper() {
		return super.getCanSpawnHere();
	}

	public boolean do_getCanSpawnHereSuper(double d, double d1, double d2) {
		return super.getCanSpawnHere(d, d1, d2);
	}

	public float do_getContractLimitDaysSuper() {
		return super.getContractLimitDays();
	}

	public ItemStack do_getHeldItemSuper() {
		return super.getHeldItem();
	}

	public float do_getInterestedAngleSuper(float f) {
		return super.getInterestedAngle(f);
	}

	public int do_getItemIconSuper(ItemStack itemstack, int i) {
		return super.getItemIcon(itemstack, i);
	}

	public int do_getLittleMaidCountSuper() {
		return super.getLittleMaidCount();
	}

	public String do_getLittleMaidModeSuper() {
		return super.getLittleMaidMode();
	}

	public int do_getMaidColorSuper() {
		return super.getMaidColor();
	}

	public EntityPlayer do_getMaidMasterEntitySuper() {
		return super.getMaidMasterEntity();
	}

	public String do_getMaidMasterSuper() {
		return super.getMaidMaster();
	}

	public int do_getMaidModeSuper() {
		return super.getMaidMode();
	}

	public int do_getMaxHealthSuper() {
		return super.getMaxHealth();
	}

	public int do_getMaxSpawnedInChunkSuper() {
		return super.getMaxSpawnedInChunk();
	}

	public double do_getMountedYOffsetSuper() {
		return super.getMountedYOffset();
	}

	public boolean do_getNextEquipItemSuper() {
		return super.getNextEquipItem();
	}

	public int do_getTotalArmorValueSuper() {
		return super.getTotalArmorValue();
	}

	public double do_getYOffsetSuper() {
		return super.getYOffset();
	}

	public boolean do_interactSuper(EntityPlayer entityplayer) {
		return super.interact(entityplayer);
	}

	public boolean do_isBlockingSuper() {
		return super.isBlocking();
	}

	public boolean do_isBloodsuckSuper() {
		return super.isBloodsuck();
	}

	public boolean do_isFixedBowSuper() {
		return super.isFixedBow();
	}

	public boolean do_isLookSugerSuper() {
		return super.isLookSuger();
	}

	public boolean do_isMaidContractEXSuper() {
		return super.isMaidContractEX();
	}

	public boolean do_isMaidContractSuper() {
		return super.isMaidContract();
	}

	public boolean do_isMaidFreedomSuper() {
		return super.isMaidFreedom();
	}

	public boolean do_isMaidTracerSuper() {
		return super.isMaidTracer();
	}

	public boolean do_isMaidWaitExSuper() {
		return super.isMaidWaitEx();
	}

	public boolean do_isMaidWaitSuper() {
		return super.isMaidWait();
	}

	public boolean do_isNoTargetSuper() {
		return super.isNoTarget();
	}

	public boolean do_isOpenInventorySuper() {
		return super.isOpenInventory();
	}

	public boolean do_isWorkingDelaySuper() {
		return super.isWorkingDelay();
	}

	public boolean do_isWorkingSuper() {
		return super.isWorking();
	}

	public void do_onDeathSuper(DamageSource damagesource) {
		super.onDeath(damagesource);
	}

	public void do_onGuiClosedSuper() {
		super.onGuiClosed();
	}

	public void do_onItemPickupSuper(Entity entity, int i) {
		super.onItemPickup(entity, i);
	}

	public void do_onKillEntitySuper(EntityLiving entityliving) {
		super.onKillEntity(entityliving);
	}

	public void do_onLivingUpdateSuper() {
		super.onLivingUpdate();
	}

	public void do_onUpdateSuper() {
		super.onUpdate();
	}

	public void do_playLittleMaidSoundSuper(
			EnumSoundLittleMaid enumsoundlittlemaid, boolean flag) {
		super.playLittleMaidSound(enumsoundlittlemaid, flag);
	}

	public void do_readFromNBTSuper(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
	}

	public void do_setArmorTextureValueSuper() {
		super.setArmorTextureValue();
	}

	public void do_setClockMaidSuper() {
		super.setClockMaid();
	}

	public void do_setDeadSuper() {
		super.setDead();
	}

	public void do_setLooksWithInterestSuper(boolean flag) {
		super.setLooksWithInterest(flag);
	}

	public void do_setMaidColorSuper(int i) {
		super.setMaidColor(i);
	}

	public void do_setMaidContractSuper(boolean flag) {
		super.setMaidContract(flag);
	}

	public void do_setMaidFreedomSuper(boolean flag) {
		super.setMaidFreedom(flag);
	}

	public void do_setMaidMasterSuper(String s) {
		super.setMaidMaster(s);
	}

	public void do_setMaidModeSuper(int i) {
		super.setMaidMode(i);
	}

	public void do_setMaidTracerSuper(boolean flag) {
		super.setMaidTracer(flag);
	}

	public void do_setMaidWaitCountSuper(int i) {
		super.setMaidWaitCount(i);
	}

	public void do_setMaidWaitSuper(boolean flag) {
		super.setMaidWait(flag);
	}

	public void do_setMaskedMaidSuper() {
		super.setMaskedMaid();
	}

	public void do_setNextTexturePackegeSuper(int i) {
		super.setNextTexturePackege(i);
	}

	public void do_setOpenInventorySuper(boolean flag) {
		super.setOpenInventory(flag);
	}

	public void do_setPathToTileSuper(boolean flag) {
		super.setPathToTile(flag);
	}

	public void do_setPrevTexturePackegeSuper(int i) {
		super.setPrevTexturePackege(i);
	}

	public void do_setSwingSuper(int i, EnumSoundLittleMaid enumsoundlittlemaid) {
		super.setSwing(i, enumsoundlittlemaid);
	}

	public void do_setTextureValueSuper() {
		super.setTextureValue();
	}

	public void do_setWorkingSuper(boolean flag) {
		super.setWorking(flag);
	}

	public void do_showHappyFXSuper() {
		super.showHappyFX();
	}

	public void do_showParticleFXSuper(String s, double d, double d1,
			double d2, double d3, double d4, double d5) {
		super.showParticleFX(s, d, d1, d2, d3, d4, d5);
	}

	public EntityAnimal do_spawnBabyAnimalSuper(EntityAnimal entityanimal) {
		return super.spawnBabyAnimal(entityanimal);
	}

	public void do_updateRiddenSuper() {
		super.updateRidden();
	}

	public void do_usePotionTotargetSuper(EntityLiving entityliving) {
		super.usePotionTotarget(entityliving);
	}

	public void do_writeToNBTSuper(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
	}

	@Override
	public void eatSugar(boolean flag, boolean flag1) {
		eatSugarSuper(flag, flag1);
	}

	public void eatSugarSuper(boolean flag, boolean flag1) {
		do_eatSugarSuper(flag, flag1);
	}

	@Override
	public void faceEntity(Entity entity, float f, float f1) {
		faceEntitySuper(entity, f, f1);
	}

	public void faceEntitySuper(Entity entity, float f, float f1) {
		do_faceEntitySuper(entity, f, f1);
	}

	@Override
	public void faceXYZ(int i, int j, int k, float f, float f1) {
		faceXYZSuper(i, j, k, f, f1);
	}

	public void faceXYZSuper(int i, int j, int k, float f, float f1) {
		do_faceXYZSuper(i, j, k, f, f1);
	}

	@Override
	public float getBlockPathWeight(int i, int j, int k) {
		return getBlockPathWeightSuper(i, j, k);
	}

	public float getBlockPathWeightSuper(int i, int j, int k) {
		return do_getBlockPathWeightSuper(i, j, k);
	}

	@Override
	public boolean getCanSpawnHere() {
		return getCanSpawnHereSuper();
	}

	@Override
	public boolean getCanSpawnHere(double d, double d1, double d2) {
		return getCanSpawnHereSuper(d, d1, d2);
	}

	public boolean getCanSpawnHereSuper() {
		return do_getCanSpawnHereSuper();
	}

	public boolean getCanSpawnHereSuper(double d, double d1, double d2) {
		return do_getCanSpawnHereSuper(d, d1, d2);
	}

	@Override
	public float getContractLimitDays() {
		return getContractLimitDaysSuper();
	}

	public float getContractLimitDaysSuper() {
		return do_getContractLimitDaysSuper();
	}

	@Override
	public ItemStack getHeldItem() {
		return getHeldItemSuper();
	}

	public ItemStack getHeldItemSuper() {
		return do_getHeldItemSuper();
	}

	@Override
	public float getInterestedAngle(float f) {
		return getInterestedAngleSuper(f);
	}

	public float getInterestedAngleSuper(float f) {
		return do_getInterestedAngleSuper(f);
	}

	@Override
	public int getItemIcon(ItemStack itemstack, int i) {
		return getItemIconSuper(itemstack, i);
	}

	public int getItemIconSuper(ItemStack itemstack, int i) {
		return do_getItemIconSuper(itemstack, i);
	}

	@Override
	public int getLittleMaidCount() {
		return getLittleMaidCountSuper();
	}

	public int getLittleMaidCountSuper() {
		return do_getLittleMaidCountSuper();
	}

	@Override
	public String getLittleMaidMode() {
		return getLittleMaidModeSuper();
	}

	public String getLittleMaidModeSuper() {
		return do_getLittleMaidModeSuper();
	}

	@Override
	public int getMaidColor() {
		return getMaidColorSuper();
	}

	public int getMaidColorSuper() {
		return do_getMaidColorSuper();
	}

	@Override
	public String getMaidMaster() {
		return getMaidMasterSuper();
	}

	@Override
	public EntityPlayer getMaidMasterEntity() {
		return getMaidMasterEntitySuper();
	}

	public EntityPlayer getMaidMasterEntitySuper() {
		return do_getMaidMasterEntitySuper();
	}

	public String getMaidMasterSuper() {
		return do_getMaidMasterSuper();
	}

	@Override
	public int getMaidMode() {
		return getMaidModeSuper();
	}

	public int getMaidModeSuper() {
		return do_getMaidModeSuper();
	}

	@Override
	public int getMaxHealth() {
		return getMaxHealthSuper();
	}

	public int getMaxHealthSuper() {
		return do_getMaxHealthSuper();
	}

	@Override
	public int getMaxSpawnedInChunk() {
		return getMaxSpawnedInChunkSuper();
	}

	public int getMaxSpawnedInChunkSuper() {
		return do_getMaxSpawnedInChunkSuper();
	}

	@Override
	public double getMountedYOffset() {
		return getMountedYOffsetSuper();
	}

	public double getMountedYOffsetSuper() {
		return do_getMountedYOffsetSuper();
	}

	@Override
	public boolean getNextEquipItem() {
		return getNextEquipItemSuper();
	}

	public boolean getNextEquipItemSuper() {
		return do_getNextEquipItemSuper();
	}

	@Override
	public int getTotalArmorValue() {
		return getTotalArmorValueSuper();
	}

	public int getTotalArmorValueSuper() {
		return do_getTotalArmorValueSuper();
	}

	@Override
	public double getYOffset() {
		return getYOffsetSuper();
	}

	public double getYOffsetSuper() {
		return do_getYOffsetSuper();
	}

	@Override
	public boolean interact(EntityPlayer entityplayer) {
		return interactSuper(entityplayer);
	}

	public boolean interactSuper(EntityPlayer entityplayer) {
		return do_interactSuper(entityplayer);
	}

	@Override
	public boolean isBlocking() {
		return isBlockingSuper();
	}

	public boolean isBlockingSuper() {
		return do_isBlockingSuper();
	}

	@Override
	public boolean isBloodsuck() {
		return isBloodsuckSuper();
	}

	public boolean isBloodsuckSuper() {
		return do_isBloodsuckSuper();
	}

	@Override
	public boolean isFixedBow() {
		return isFixedBowSuper();
	}

	public boolean isFixedBowSuper() {
		return do_isFixedBowSuper();
	}

	@Override
	public boolean isLookSuger() {
		return isLookSugerSuper();
	}

	public boolean isLookSugerSuper() {
		return do_isLookSugerSuper();
	}

	@Override
	public boolean isMaidContract() {
		return isMaidContractSuper();
	}

	@Override
	public boolean isMaidContractEX() {
		return isMaidContractEXSuper();
	}

	public boolean isMaidContractEXSuper() {
		return do_isMaidContractEXSuper();
	}

	public boolean isMaidContractSuper() {
		return do_isMaidContractSuper();
	}

	@Override
	public boolean isMaidFreedom() {
		return isMaidFreedomSuper();
	}

	public boolean isMaidFreedomSuper() {
		return do_isMaidFreedomSuper();
	}

	@Override
	public boolean isMaidTracer() {
		return isMaidTracerSuper();
	}

	public boolean isMaidTracerSuper() {
		return do_isMaidTracerSuper();
	}

	@Override
	public boolean isMaidWait() {
		return isMaidWaitSuper();
	}

	@Override
	public boolean isMaidWaitEx() {
		return isMaidWaitExSuper();
	}

	public boolean isMaidWaitExSuper() {
		return do_isMaidWaitExSuper();
	}

	public boolean isMaidWaitSuper() {
		return do_isMaidWaitSuper();
	}

	@Override
	public boolean isNoTarget() {
		return isNoTargetSuper();
	}

	public boolean isNoTargetSuper() {
		return do_isNoTargetSuper();
	}

	@Override
	public boolean isOpenInventory() {
		return isOpenInventorySuper();
	}

	public boolean isOpenInventorySuper() {
		return do_isOpenInventorySuper();
	}

	@Override
	public boolean isWorking() {
		return isWorkingSuper();
	}

	@Override
	public boolean isWorkingDelay() {
		return isWorkingDelaySuper();
	}

	public boolean isWorkingDelaySuper() {
		return do_isWorkingDelaySuper();
	}

	public boolean isWorkingSuper() {
		return do_isWorkingSuper();
	}

	@Override
	public void onDeath(DamageSource damagesource) {
		onDeathSuper(damagesource);
	}

	public void onDeathSuper(DamageSource damagesource) {
		do_onDeathSuper(damagesource);
	}

	@Override
	public void onGuiClosed() {
		onGuiClosedSuper();
	}

	public void onGuiClosedSuper() {
		do_onGuiClosedSuper();
	}

	@Override
	public void onItemPickup(Entity entity, int i) {
		onItemPickupSuper(entity, i);
	}

	public void onItemPickupSuper(Entity entity, int i) {
		do_onItemPickupSuper(entity, i);
	}

	@Override
	public void onKillEntity(EntityLiving entityliving) {
		onKillEntitySuper(entityliving);
	}

	public void onKillEntitySuper(EntityLiving entityliving) {
		do_onKillEntitySuper(entityliving);
	}

	@Override
	public void onLivingUpdate() {
		onLivingUpdateSuper();
	}

	public void onLivingUpdateSuper() {
		do_onLivingUpdateSuper();
	}

	@Override
	public void onUpdate() {
		onUpdateSuper();
	}

	public void onUpdateSuper() {
		do_onUpdateSuper();
	}

	@Override
	public void playLittleMaidSound(EnumSoundLittleMaid enumsoundlittlemaid,
			boolean flag) {
		playLittleMaidSoundSuper(enumsoundlittlemaid, flag);
	}

	public void playLittleMaidSoundSuper(
			EnumSoundLittleMaid enumsoundlittlemaid, boolean flag) {
		do_playLittleMaidSoundSuper(enumsoundlittlemaid, flag);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		readFromNBTSuper(nbttagcompound);
	}

	public void readFromNBTSuper(NBTTagCompound nbttagcompound) {
		do_readFromNBTSuper(nbttagcompound);
	}

	@Override
	public void setArmorTextureValue() {
		setArmorTextureValueSuper();
	}

	public void setArmorTextureValueSuper() {
		do_setArmorTextureValueSuper();
	}

	@Override
	public void setClockMaid() {
		setClockMaidSuper();
	}

	public void setClockMaidSuper() {
		do_setClockMaidSuper();
	}

	@Override
	public void setDead() {
		setDeadSuper();
	}

	public void setDeadSuper() {
		do_setDeadSuper();
	}

	@Override
	public void setLooksWithInterest(boolean flag) {
		setLooksWithInterestSuper(flag);
	}

	public void setLooksWithInterestSuper(boolean flag) {
		do_setLooksWithInterestSuper(flag);
	}

	@Override
	public void setMaidColor(int i) {
		setMaidColorSuper(i);
	}

	public void setMaidColorSuper(int i) {
		do_setMaidColorSuper(i);
	}

	@Override
	public void setMaidContract(boolean flag) {
		setMaidContractSuper(flag);
	}

	public void setMaidContractSuper(boolean flag) {
		do_setMaidContractSuper(flag);
	}

	@Override
	public void setMaidFreedom(boolean flag) {
		setMaidFreedomSuper(flag);
	}

	public void setMaidFreedomSuper(boolean flag) {
		do_setMaidFreedomSuper(flag);
	}

	@Override
	public void setMaidMaster(String s) {
		setMaidMasterSuper(s);
	}

	public void setMaidMasterSuper(String s) {
		do_setMaidMasterSuper(s);
	}

	@Override
	public void setMaidMode(int i) {
		setMaidModeSuper(i);
	}

	public void setMaidModeSuper(int i) {
		do_setMaidModeSuper(i);
	}

	@Override
	public void setMaidTracer(boolean flag) {
		setMaidTracerSuper(flag);
	}

	public void setMaidTracerSuper(boolean flag) {
		do_setMaidTracerSuper(flag);
	}

	@Override
	public void setMaidWait(boolean flag) {
		setMaidWaitSuper(flag);
	}

	@Override
	public void setMaidWaitCount(int i) {
		setMaidWaitCountSuper(i);
	}

	public void setMaidWaitCountSuper(int i) {
		do_setMaidWaitCountSuper(i);
	}

	public void setMaidWaitSuper(boolean flag) {
		do_setMaidWaitSuper(flag);
	}

	@Override
	public void setMaskedMaid() {
		setMaskedMaidSuper();
	}

	public void setMaskedMaidSuper() {
		do_setMaskedMaidSuper();
	}

	@Override
	public void setNextTexturePackege(int i) {
		setNextTexturePackegeSuper(i);
	}

	public void setNextTexturePackegeSuper(int i) {
		do_setNextTexturePackegeSuper(i);
	}

	@Override
	public void setOpenInventory(boolean flag) {
		setOpenInventorySuper(flag);
	}

	public void setOpenInventorySuper(boolean flag) {
		do_setOpenInventorySuper(flag);
	}

	@Override
	public void setPathToTile(boolean flag) {
		setPathToTileSuper(flag);
	}

	public void setPathToTileSuper(boolean flag) {
		do_setPathToTileSuper(flag);
	}

	@Override
	public void setPrevTexturePackege(int i) {
		setPrevTexturePackegeSuper(i);
	}

	public void setPrevTexturePackegeSuper(int i) {
		do_setPrevTexturePackegeSuper(i);
	}

	@Override
	public void setSwing(int i, EnumSoundLittleMaid enumsoundlittlemaid) {
		setSwingSuper(i, enumsoundlittlemaid);
	}

	public void setSwingSuper(int i, EnumSoundLittleMaid enumsoundlittlemaid) {
		do_setSwingSuper(i, enumsoundlittlemaid);
	}

	@Override
	public void setTextureValue() {
		setTextureValueSuper();
	}

	public void setTextureValueSuper() {
		do_setTextureValueSuper();
	}

	@Override
	public void setWorking(boolean flag) {
		setWorkingSuper(flag);
	}

	public void setWorkingSuper(boolean flag) {
		do_setWorkingSuper(flag);
	}

	@Override
	public void showHappyFX() {
		showHappyFXSuper();
	}

	public void showHappyFXSuper() {
		do_showHappyFXSuper();
	}

	@Override
	public void showParticleFX(String s, double d, double d1, double d2,
			double d3, double d4, double d5) {
		showParticleFXSuper(s, d, d1, d2, d3, d4, d5);
	}

	public void showParticleFXSuper(String s, double d, double d1, double d2,
			double d3, double d4, double d5) {
		do_showParticleFXSuper(s, d, d1, d2, d3, d4, d5);
	}

	@Override
	public EntityAnimal spawnBabyAnimal(EntityAnimal entityanimal) {
		return spawnBabyAnimalSuper(entityanimal);
	}

	public EntityAnimal spawnBabyAnimalSuper(EntityAnimal entityanimal) {
		return do_spawnBabyAnimalSuper(entityanimal);
	}

	@Override
	public void updateRidden() {
		updateRiddenSuper();
	}

	public void updateRiddenSuper() {
		do_updateRiddenSuper();
	}

	@Override
	public void usePotionTotarget(EntityLiving entityliving) {
		usePotionTotargetSuper(entityliving);
	}

	public void usePotionTotargetSuper(EntityLiving entityliving) {
		do_usePotionTotargetSuper(entityliving);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		writeToNBTSuper(nbttagcompound);
	}

	public void writeToNBTSuper(NBTTagCompound nbttagcompound) {
		do_writeToNBTSuper(nbttagcompound);
	}

	@Override
	protected int applyPotionDamageCalculations(DamageSource damagesource, int i) {
		return applyPotionDamageCalculationsSuper(damagesource, i);
	}

	protected int applyPotionDamageCalculationsSuper(DamageSource damagesource,
			int i) {
		return do_applyPotionDamageCalculationsSuper(damagesource, i);
	}

	@Override
	protected void attackEntity(Entity entity, float f) {
		attackEntitySuper(entity, f);
	}

	protected void attackEntitySuper(Entity entity, float f) {
		do_attackEntitySuper(entity, f);
	}

	@Override
	protected boolean canBlockBeSeen(int i, int j, int k, boolean flag,
			boolean flag1, boolean flag2) {
		return canBlockBeSeenSuper(i, j, k, flag, flag1, flag2);
	}

	protected boolean canBlockBeSeenSuper(int i, int j, int k, boolean flag,
			boolean flag1, boolean flag2) {
		return do_canBlockBeSeenSuper(i, j, k, flag, flag1, flag2);
	}

	@Override
	protected boolean canDespawn() {
		return canDespawnSuper();
	}

	protected boolean canDespawnSuper() {
		return do_canDespawnSuper();
	}

	@Override
	protected boolean checkSnows(int i, int j, int k) {
		return checkSnowsSuper(i, j, k);
	}

	protected boolean checkSnowsSuper(int i, int j, int k) {
		return do_checkSnowsSuper(i, j, k);
	}

	@Override
	protected void damageArmor(int i) {
		damageArmorSuper(i);
	}

	protected void damageArmorSuper(int i) {
		do_damageArmorSuper(i);
	}

	@Override
	protected void damageEntity(DamageSource damagesource, int i) {
		damageEntitySuper(damagesource, i);
	}

	protected void damageEntitySuper(DamageSource damagesource, int i) {
		do_damageEntitySuper(damagesource, i);
	}

	@Override
	protected int decreaseAirSupply(int i) {
		return decreaseAirSupplySuper(i);
	}

	protected int decreaseAirSupplySuper(int i) {
		return do_decreaseAirSupplySuper(i);
	}

	protected int do_applyPotionDamageCalculationsSuper(
			DamageSource damagesource, int i) {
		return super.applyPotionDamageCalculations(damagesource, i);
	}

	protected void do_attackEntitySuper(Entity entity, float f) {
		super.attackEntity(entity, f);
	}

	protected boolean do_canBlockBeSeenSuper(int i, int j, int k, boolean flag,
			boolean flag1, boolean flag2) {
		return super.canBlockBeSeen(i, j, k, flag, flag1, flag2);
	}

	protected boolean do_canDespawnSuper() {
		return super.canDespawn();
	}

	protected boolean do_checkSnowsSuper(int i, int j, int k) {
		return super.checkSnows(i, j, k);
	}

	protected void do_damageArmorSuper(int i) {
		super.damageArmor(i);
	}

	protected void do_damageEntitySuper(DamageSource damagesource, int i) {
		super.damageEntity(damagesource, i);
	}

	protected int do_decreaseAirSupplySuper(int i) {
		return super.decreaseAirSupply(i);
	}

	protected void do_dropFewItemsSuper(boolean flag, int i) {
		super.dropFewItems(flag, i);
	}

	protected Entity do_findPlayerToAttackSuper() {
		return super.findPlayerToAttack();
	}

	protected int do_getBlockLightingSuper(int i, int j, int k) {
		return super.getBlockLighting(i, j, k);
	}

	protected String do_getDeathSoundSuper() {
		return super.getDeathSound();
	}

	protected int do_getDropItemIdSuper() {
		return super.getDropItemId();
	}

	protected String do_getHurtSoundSuper() {
		return super.getHurtSound();
	}

	protected boolean do_getIFFSuper(Entity entity) {
		return super.getIFF(entity);
	}

	protected String do_getLivingSoundSuper() {
		return super.getLivingSound();
	}

	protected void do_getWeaponStatusSuper() {
		super.getWeaponStatus();
	}

	protected void do_givemeSugerSuper(float f, float f1) {
		super.givemeSuger(f, f1);
	}

	protected boolean do_isAIEnabledSuper() {
		return super.isAIEnabled();
	}

	protected boolean do_isMovementCeasedSuper() {
		return super.isMovementCeased();
	}

	protected void do_moveCookingSuper() {
		super.moveCooking();
	}

	protected void do_moveEscorterSuper() {
		super.moveEscorter();
	}

	protected void do_movePharmacistSuper() {
		super.movePharmacist();
	}

	protected boolean do_movePlayingSuper() {
		return super.movePlaying();
	}

	protected void do_moveTorcherSuper() {
		super.moveTorcher();
	}

	protected boolean do_moveTracerSuper() {
		return super.moveTracer();
	}

	protected NBTTagList do_newIntNBTListSuper(int[] ai) {
		return super.newIntNBTList(ai);
	}

	protected void do_playingSuper() {
		super.playing();
	}

	protected boolean do_pushOutOfBlocksSuper(double d, double d1, double d2) {
		return super.pushOutOfBlocks(d, d1, d2);
	}

	protected void do_putChestSuper() {
		super.putChest();
	}

	protected void do_setHomePositionSuper() {
		super.setHomePosition();
	}

	protected float do_setLittleMaidFlashTimeSuper(float f) {
		return super.setLittleMaidFlashTime(f);
	}

	protected void do_updateEntityActionStateSuper() {
		super.updateEntityActionState();
	}

	protected float do_updateRotationSuper(float f, float f1, float f2) {
		return super.updateRotation(f, f1, f2);
	}

	@Override
	protected void dropFewItems(boolean flag, int i) {
		dropFewItemsSuper(flag, i);
	}

	protected void dropFewItemsSuper(boolean flag, int i) {
		do_dropFewItemsSuper(flag, i);
	}

	@Override
	protected Entity findPlayerToAttack() {
		return findPlayerToAttackSuper();
	}

	protected Entity findPlayerToAttackSuper() {
		return do_findPlayerToAttackSuper();
	}

	@Override
	protected int getBlockLighting(int i, int j, int k) {
		return getBlockLightingSuper(i, j, k);
	}

	protected int getBlockLightingSuper(int i, int j, int k) {
		return do_getBlockLightingSuper(i, j, k);
	}

	@Override
	protected String getDeathSound() {
		return getDeathSoundSuper();
	}

	protected String getDeathSoundSuper() {
		return do_getDeathSoundSuper();
	}

	@Override
	protected int getDropItemId() {
		return getDropItemIdSuper();
	}

	protected int getDropItemIdSuper() {
		return do_getDropItemIdSuper();
	}

	@Override
	protected String getHurtSound() {
		return getHurtSoundSuper();
	}

	protected String getHurtSoundSuper() {
		return do_getHurtSoundSuper();
	}

	@Override
	protected boolean getIFF(Entity entity) {
		return getIFFSuper(entity);
	}

	protected boolean getIFFSuper(Entity entity) {
		return do_getIFFSuper(entity);
	}

	@Override
	protected String getLivingSound() {
		return getLivingSoundSuper();
	}

	protected String getLivingSoundSuper() {
		return do_getLivingSoundSuper();
	}

	@Override
	protected void getWeaponStatus() {
		getWeaponStatusSuper();
	}

	protected void getWeaponStatusSuper() {
		do_getWeaponStatusSuper();
	}

	@Override
	protected void givemeSuger(float f, float f1) {
		givemeSugerSuper(f, f1);
	}

	protected void givemeSugerSuper(float f, float f1) {
		do_givemeSugerSuper(f, f1);
	}

	@Override
	protected boolean isAIEnabled() {
		return isAIEnabledSuper();
	}

	protected boolean isAIEnabledSuper() {
		return do_isAIEnabledSuper();
	}

	@Override
	protected boolean isMovementCeased() {
		return isMovementCeasedSuper();
	}

	protected boolean isMovementCeasedSuper() {
		return do_isMovementCeasedSuper();
	}

	@Override
	protected void moveCooking() {
		moveCookingSuper();
	}

	protected void moveCookingSuper() {
		do_moveCookingSuper();
	}

	@Override
	protected void moveEscorter() {
		moveEscorterSuper();
	}

	protected void moveEscorterSuper() {
		do_moveEscorterSuper();
	}

	@Override
	protected void movePharmacist() {
		movePharmacistSuper();
	}

	protected void movePharmacistSuper() {
		do_movePharmacistSuper();
	}

	@Override
	protected boolean movePlaying() {
		return movePlayingSuper();
	}

	protected boolean movePlayingSuper() {
		return do_movePlayingSuper();
	}

	@Override
	protected void moveTorcher() {
		moveTorcherSuper();
	}

	protected void moveTorcherSuper() {
		do_moveTorcherSuper();
	}

	@Override
	protected boolean moveTracer() {
		return moveTracerSuper();
	}

	protected boolean moveTracerSuper() {
		return do_moveTracerSuper();
	}

	@Override
	protected NBTTagList newIntNBTList(int[] ai) {
		return newIntNBTListSuper(ai);
	}

	protected NBTTagList newIntNBTListSuper(int[] ai) {
		return do_newIntNBTListSuper(ai);
	}

	@Override
	protected void playing() {
		playingSuper();
	}

	protected void playingSuper() {
		do_playingSuper();
	}

	@Override
	protected boolean pushOutOfBlocks(double d, double d1, double d2) {
		return pushOutOfBlocksSuper(d, d1, d2);
	}

	protected boolean pushOutOfBlocksSuper(double d, double d1, double d2) {
		return do_pushOutOfBlocksSuper(d, d1, d2);
	}

	@Override
	protected void putChest() {
		putChestSuper();
	}

	protected void putChestSuper() {
		do_putChestSuper();
	}

	@Override
	protected void setHomePosition() {
		setHomePositionSuper();
	}

	protected void setHomePositionSuper() {
		do_setHomePositionSuper();
	}

	@Override
	protected float setLittleMaidFlashTime(float f) {
		return setLittleMaidFlashTimeSuper(f);
	}

	protected float setLittleMaidFlashTimeSuper(float f) {
		return do_setLittleMaidFlashTimeSuper(f);
	}

	@Override
	protected void updateEntityActionState() {
		updateEntityActionStateSuper();
	}

	protected void updateEntityActionStateSuper() {
		do_updateEntityActionStateSuper();
	}

	@Override
	protected float updateRotation(float f, float f1, float f2) {
		return updateRotationSuper(f, f1, f2);
	}

	protected float updateRotationSuper(float f, float f1, float f2) {
		return do_updateRotationSuper(f, f1, f2);
	}
}
