package net.minecraft.src;


public interface INamePlatePakage {

	public abstract void refreshNamePlateList();

	public abstract Entity getNamePlate(EntityLittleMaid maid);

	public abstract void moveWorldNamePlate(EntityLittleMaid srcMaid,
			EntityLittleMaid distMaid, World prevWorld, World newWorld,
			EntityPosition ep);

	public abstract void moveNamePlate(EntityLittleMaid srcMaid,
			EntityLittleMaid distMaid);

}