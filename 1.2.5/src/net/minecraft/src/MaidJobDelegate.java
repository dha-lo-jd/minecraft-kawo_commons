package net.minecraft.src;

public abstract class MaidJobDelegate<E extends EntityLittleMaid> implements
		IMaidJob {

	protected E maid;

	public MaidJobDelegate(E maid) {
		super();
		this.maid = maid;
	}

	@Override
	public void doUpdateJob() {
		doUpdateJob(maid);
	}

	protected abstract void doUpdateJob(E maid);
}
