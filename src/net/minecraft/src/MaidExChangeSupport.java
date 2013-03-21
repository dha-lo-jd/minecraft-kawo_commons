package net.minecraft.src;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.Minecraft;

public class MaidExChangeSupport {

	public interface IMaidFactory {
		public EntityLittleMaid createMaid(World world);

		public EntityLittleMaid createMaidByBase(World world,
				EntityLittleMaid maid);
	}

	public static final MaidExChangeSupport CHANGE_SUPPORT_DEFUALT = new MaidExChangeSupport(
			new MaidExChangeSupport.IMaidFactory() {
				@Override
				public EntityLittleMaid createMaidByBase(World world,
						EntityLittleMaid maid) {
					EntityLittleMaid entityLittleMaid = new EntityLittleMaid(
							world);
					maidCPS.copyProperties(maid, entityLittleMaid);
					entityLittleMaid.maidInventory.entitylittlemaid = entityLittleMaid;
					if (world != null) {
						entityLittleMaid.maidAvatarEntity = new EntityLittleMaidAvatar(
								world, entityLittleMaid);
						GuiLittleMaidIFF.initIFF(world);
					}
					entityLittleMaid.setPosition(maid.posX, maid.posY,
							maid.posZ);

					return entityLittleMaid;
				}

				@Override
				public EntityLittleMaid createMaid(World world) {
					return new EntityLittleMaid(world);
				}
			});

	private static final CopyPropertiesSupport<EntityLittleMaid> maidCPS = new CopyPropertiesSupport<EntityLittleMaid>(
			EntityLittleMaid.class);

	private static final Map<String, MaidExChangeSupport> map = new HashMap<String, MaidExChangeSupport>();

	public static void copyProperties(EntityLittleMaid srcMaid,
			EntityLittleMaid distMaid) {
		maidCPS.copyProperties(srcMaid, distMaid);
	}

	public static EntityLittleMaid createClone(EntityLittleMaid srcMaid,
			EntityLittleMaid distMaid) {
		maidCPS.copyProperties(srcMaid, distMaid);
		distMaid.maidInventory.entitylittlemaid = distMaid;
		if (distMaid.worldObj != null) {
			distMaid.maidAvatarEntity = new EntityLittleMaidAvatar(
					distMaid.worldObj, distMaid);
			GuiLittleMaidIFF.initIFF(distMaid.worldObj);
		}
		return distMaid;
	}

	public static MaidExChangeSupport getMaidExChangeSupport(Class cls) {
		return getMaidExChangeSupport(cls.getName());
	}

	public static MaidExChangeSupport getMaidExChangeSupport(String clsName) {
		if (map.containsKey(clsName)) {
			return map.get(clsName);

		}
		return CHANGE_SUPPORT_DEFUALT;
	}

	public static void registMaidExChangeSupportType(Class cls,
			MaidExChangeSupport support) {
		registMaidExChangeSupportType(cls.getName(), support);
	}

	public static void registMaidExChangeSupportType(String clsName,
			MaidExChangeSupport support) {
		map.put(clsName, support);
	}

	private final IMaidFactory factory;

	public MaidExChangeSupport(IMaidFactory factory) {
		this.factory = factory;
	}

	public EntityLittleMaid changeMaid(EntityLittleMaid maid) {
		Minecraft mc = ModLoader.getMinecraftInstance();
		if (mc.currentScreen != null) {
			mc.displayGuiScreen(null);
		}
		World world = maid.worldObj;

		EntityLittleMaid par1Entity = factory.createMaidByBase(world, maid);
		world.spawnEntityInWorld(par1Entity);
		mod_Dha_lo_jd_Commons.MAID_MANAGER.refreshNamePlateList();
		mod_Dha_lo_jd_Commons.MAID_MANAGER.moveNamePlate(maid,
				(EntityLittleMaid) par1Entity);
		world.setEntityDead(maid);

		return par1Entity;
	}

	protected EntityLittleMaid createMaid(World world) {
		EntityLittleMaid par1Entity = factory.createMaid(world);
		return par1Entity;
	}

}
