package net.minecraft.src;

public abstract class MaidInteractJobDelegate<E extends EntityLittleMaid>
		extends MaidJobDelegate<E> implements IMaidJobInteract {

	boolean standBy;

	public MaidInteractJobDelegate(E maid) {
		super(maid);
	}

	@Override
	public boolean interact(EntityPlayer entityplayer) {
		if (standBy) {
			return doInteract(entityplayer);
		}
		return false;
	}

	@Override
	public void standByInteract(EntityPlayer entityplayer) {
		standBy = shouldInteract(entityplayer);
	}

	abstract boolean doInteract(EntityPlayer entityplayer);

	abstract boolean shouldInteract(EntityPlayer entityplayer);

}
