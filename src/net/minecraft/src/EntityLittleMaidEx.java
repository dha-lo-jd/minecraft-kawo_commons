package net.minecraft.src;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public abstract class EntityLittleMaidEx extends EntityLittleMaidExProxy {

	protected final List<IMaidJobEquipItem> IMaidJobEquipItemList = new ArrayList<IMaidJobEquipItem>();

	protected final List<IMaidJobInCombat> IMaidJobInCombatList = new ArrayList<IMaidJobInCombat>();

	protected final List<IMaidJobInteract> IMaidJobInteractList = new ArrayList<IMaidJobInteract>();

	protected final List<IMaidJob> IMaidJobList = new ArrayList<IMaidJob>();

	protected final List<IMaidJobMaidInventory<EntityLittleMaid>> IMaidJobMaidInventoryList = new ArrayList<IMaidJobMaidInventory<EntityLittleMaid>>();

	protected final List<IMaidJobMaidMode> IMaidJobMaidModeList = new ArrayList<IMaidJobMaidMode>();

	protected final List<IMaidJobPlaying> IMaidJobPlayingList = new ArrayList<IMaidJobPlaying>();

	protected final List<IMaidJobTagCompound> IMaidJobTagCompoundList = new ArrayList<IMaidJobTagCompound>();

	protected LittleMaidBaseJobDelegate baseJobDelegate = new LittleMaidBaseJobDelegate(
			this);

	public EntityLittleMaidEx(World world) {
		super(world);
	}

	public static abstract class ChildEntityFactory<T extends EntityLittleMaidExChild, M extends EntityLittleMaidEx> {
		protected final Class cls;
		protected final M maid;

		public ChildEntityFactory(Class<T> cls, M maid) {
			super();
			this.cls = cls;
			this.maid = maid;
		}

		public boolean tryCreateInstance() {
			if (maid.getChildEntity(cls) != null || !shouldCreate()) {
				return false;
			}

			T child = createInstance();
			if (child == null) {
				return false;
			}

			maid.registChild(cls, child);

			return true;
		}

		abstract protected boolean shouldCreate();

		abstract protected T createInstance();
	}

	private Map<Class, ChildEntityFactory> factoryMap = new HashMap<Class, ChildEntityFactory>();

	private Map<Class, EntityLittleMaidExChild> childEntityMap = new HashMap<Class, EntityLittleMaidExChild>();

	public EntityLittleMaidExChild getChildEntity(Class cls) {
		return childEntityMap.get(cls);
	}

	public Collection<EntityLittleMaidExChild> getChildEntitySet() {
		return childEntityMap.values();
	}

	public <T extends EntityLittleMaidExChild> void registChildFactory(
			Class<T> cls, ChildEntityFactory<T, ?> factory) {
		factoryMap.put(cls, factory);
	}

	private void registChild(Class cls, EntityLittleMaidExChild entity) {
		childEntityMap.put(cls, entity);
	}

	@Override
	public void onUpdate() {
		for (ChildEntityFactory factory : factoryMap.values()) {
			factory.tryCreateInstance();
		}
		for (Entity entity : getChildEntitySet()) {
			entity.lastTickPosX = entity.posX;
			entity.lastTickPosY = entity.posY;
			entity.lastTickPosZ = entity.posZ;
		}
		super.onUpdate();
		for (Entity entity : getChildEntitySet()) {
			entity.onUpdate();
		}
	}

	protected void registJob(IMaidJob job) {
		IMaidJobList.add(0, job);
		if (job instanceof IMaidJobEquipItem) {
			IMaidJobEquipItemList.add(0, (IMaidJobEquipItem) job);
		}
		if (job instanceof IMaidJobInCombat) {
			IMaidJobInCombatList.add(0, (IMaidJobInCombat) job);
		}
		if (job instanceof IMaidJobInteract) {
			IMaidJobInteractList.add(0, (IMaidJobInteract) job);
		}
		if (job instanceof IMaidJobMaidInventory) {
			IMaidJobMaidInventoryList.add(0, (IMaidJobMaidInventory) job);
		}
		if (job instanceof IMaidJobMaidMode) {
			IMaidJobMaidModeList.add(0, (IMaidJobMaidMode) job);
		}
		if (job instanceof IMaidJobPlaying) {
			IMaidJobPlayingList.add(0, (IMaidJobPlaying) job);
		}
		if (job instanceof IMaidJobTagCompound) {
			IMaidJobTagCompoundList.add(0, (IMaidJobTagCompound) job);
		}
	}

	public MaidExChangeSupport getMaidExChangeSupport() {
		return MaidExChangeSupport.CHANGE_SUPPORT_DEFUALT;
	}

	@Override
	public boolean interact(EntityPlayer entityplayer) {
		for (IMaidJobInteract jobInteract : IMaidJobInteractList) {
			jobInteract.standByInteract(entityplayer);
		}
		boolean result = super.interact(entityplayer);
		for (IMaidJobInteract jobInteract : IMaidJobInteractList) {
			jobInteract.interact(entityplayer);
		}
		return result;
	}

	@Override
	protected void playing() {
		super.playing();
		for (IMaidJobPlaying jobPlaying : IMaidJobPlayingList) {
			jobPlaying.playing();
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		for (IMaidJobTagCompound jobTagCompound : IMaidJobTagCompoundList) {
			NBTTagCompound tag = new NBTTagCompound();
			jobTagCompound.writeToNBT(tag);
			nbttagcompound.setCompoundTag(jobTagCompound.getTagName(), tag);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		for (IMaidJobTagCompound jobTagCompound : IMaidJobTagCompoundList) {
			jobTagCompound.readFromNBT(nbttagcompound
					.getCompoundTag(jobTagCompound.getTagName()));
		}
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();

		for (IMaidJob maidJob : IMaidJobList) {
			maidJob.doUpdateJob();
		}
	}

	@Override
	public boolean getNextEquipItem() {
		if (super.getNextEquipItem()) {
			return true;
		} else {
			for (IMaidJobEquipItem jobEquipItem : IMaidJobEquipItemList) {
				if (jobEquipItem.getNextEquipItem()) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public String getLittleMaidMode() {
		String s = super.getLittleMaidMode();
		for (IMaidJobMaidMode jobMaidMode : IMaidJobMaidModeList) {
			if (jobMaidMode.isOverWriteLittleMaidMode(s)) {
				s = jobMaidMode.getLittleMaidMode(s);
			}
		}
		return s;
	}

	@Override
	protected void putChest() {
		Map<Integer, ItemStack> map = new HashMap<Integer, ItemStack>();

		for (int i = 0; i < maidInventory.mainInventory.length; i++) {
			ItemStack itemStack = maidInventory.getStackInSlot(i);
			if (itemStack == null) {
				continue;
			}
			for (IMaidJobEquipItem jobEquipItem : IMaidJobEquipItemList) {
				if (jobEquipItem.isItemCanEquip(itemStack.itemID)) {
					map.put(i, itemStack);
					maidInventory.setInventorySlotContents(i, null);
				}
			}
		}

		List prevSerchedChest = new ArrayList(serchedChest);

		super.putChest();

		for (Entry<Integer, ItemStack> entry : map.entrySet()) {
			maidInventory.setInventorySlotContents(entry.getKey(),
					entry.getValue());
		}

		if (maidInventory.getFirstEmptyStack() == -1) {
			serchedChest = prevSerchedChest;
		}
	}

}
