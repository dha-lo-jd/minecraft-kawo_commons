package net.minecraft.src;


public interface IMaidJobEquipItem extends IMaidJob {
	public boolean getNextEquipItem();

	public boolean isItemCanEquip(int itemID);

	public ItemStack getHeldItem();
}
