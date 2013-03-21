package net.minecraft.src;


public class MaidManager {

	public MaidManager() {
		if (mod_Dha_lo_jd_Commons.enableModNamePlate) {
			namePlatePakage = new NamePlatePakage();
		} else {
			namePlatePakage = new DisableNamePlatePakage();
		}
	}

	private final INamePlatePakage namePlatePakage;

	public void refreshNamePlateList() {
		if (mod_Dha_lo_jd_Commons.enableModNamePlate) {
			namePlatePakage.refreshNamePlateList();
		}
	}

	public Entity getNamePlate(EntityLittleMaid maid) {
		if (mod_Dha_lo_jd_Commons.enableModNamePlate) {
			return namePlatePakage.getNamePlate(maid);
		}
		return null;
	}

	public void moveWorldNamePlate(EntityLittleMaid srcMaid,
			EntityLittleMaid distMaid, World prevWorld, World newWorld,
			EntityPosition ep) {
		if (mod_Dha_lo_jd_Commons.enableModNamePlate) {
			namePlatePakage.moveWorldNamePlate(srcMaid, distMaid, prevWorld,
					newWorld, ep);
		}
	}

	public void moveNamePlate(EntityLittleMaid srcMaid,
			EntityLittleMaid distMaid) {
		if (mod_Dha_lo_jd_Commons.enableModNamePlate) {
			namePlatePakage.moveNamePlate(srcMaid, distMaid);
		}
	}

}
