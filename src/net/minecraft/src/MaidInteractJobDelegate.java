package net.minecraft.src;

public abstract class MaidInteractJobDelegate<E extends EntityLittleMaid>
		extends MaidJobDelegate<E> implements IMaidJobInteract {

	public MaidInteractJobDelegate(E maid) {
		super(maid);
	}

	boolean standBy;

	abstract boolean shouldInteract(EntityPlayer entityplayer);

	abstract boolean doInteract(EntityPlayer entityplayer);

	@Override
	public void standByInteract(EntityPlayer entityplayer) {
		standBy = shouldInteract(entityplayer);
	}

	@Override
	public boolean interact(EntityPlayer entityplayer) {
		if (standBy) {
			return doInteract(entityplayer);
		}
		return false;
	}

}
