package net.minecraft.src;

public class DisableNamePlatePakage implements INamePlatePakage {

	@Override
	public void refreshNamePlateList() {
	}

	@Override
	public Entity getNamePlate(EntityLittleMaid maid) {
		return null;
	}

	@Override
	public void moveWorldNamePlate(EntityLittleMaid srcMaid,
			EntityLittleMaid distMaid, World prevWorld, World newWorld,
			EntityPosition ep) {
	}

	@Override
	public void moveNamePlate(EntityLittleMaid srcMaid,
			EntityLittleMaid distMaid) {
	}

}
