package net.minecraft.src;

public interface IMaidJobInteract extends IMaidJob {
	public void standByInteract(EntityPlayer entityplayer);

	public boolean interact(EntityPlayer entityplayer);
}
