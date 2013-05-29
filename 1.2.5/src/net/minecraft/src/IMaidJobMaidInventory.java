package net.minecraft.src;


public interface IMaidJobMaidInventory<E extends EntityLittleMaid> extends
		IMaidJob {
	public GuiLittleMaidInventory getSwitchInventory(E entitylittlemaid1,
			IInventory iinventory, InventoryLittleMaid inventorylittlemaid);
}
