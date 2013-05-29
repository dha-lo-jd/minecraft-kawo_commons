package net.minecraft.src;


public interface IMaidJobTagCompound extends IMaidJob {
	public String getTagName();

	public void readFromNBT(NBTTagCompound nbttagcompound);

	public void writeToNBT(NBTTagCompound nbttagcompound);
}
