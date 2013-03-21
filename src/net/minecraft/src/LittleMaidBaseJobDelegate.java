package net.minecraft.src;

public class LittleMaidBaseJobDelegate<E extends EntityLittleMaid> extends
		MaidJobDelegate<E> implements IMaidJobEquipItem, IMaidJobInCombat,
		IMaidJobInteract, IMaidJobMaidInventory<E>, IMaidJobMaidMode,
		IMaidJobPlaying, IMaidJobTagCompound {

	public LittleMaidBaseJobDelegate(E maid) {
		super(maid);
	}

	@Override
	public String getTagName() {
		return null;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
	}

	@Override
	public void playing() {
	}

	@Override
	public boolean isOverWriteLittleMaidMode(String maidMode) {
		return false;
	}

	@Override
	public String getLittleMaidMode(String maidMode) {
		return null;
	}

	@Override
	public GuiLittleMaidInventory getSwitchInventory(E entitylittlemaid1,
			IInventory iinventory, InventoryLittleMaid inventorylittlemaid) {
		return null;
	}

	@Override
	public void standByInteract(EntityPlayer entityplayer) {
	}

	@Override
	public boolean interact(EntityPlayer entityplayer) {
		return false;
	}

	@Override
	public EntityLiving getCombatEnemy() {
		return null;
	}

	@Override
	public void inCombat(EntityLiving enemy) {
	}

	@Override
	public void inPeace() {
	}

	@Override
	public boolean getNextEquipItem() {
		return false;
	}

	@Override
	public ItemStack getHeldItem() {
		return null;
	}

	@Override
	protected void doUpdateJob(E maid) {
	}

	@Override
	public boolean isItemCanEquip(int itemID) {
		return false;
	}

}
