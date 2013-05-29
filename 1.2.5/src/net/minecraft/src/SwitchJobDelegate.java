package net.minecraft.src;

public abstract class SwitchJobDelegate<E extends EntityLittleMaid, JOB extends IMaidJob>
		extends MaidJobDelegate<E> {

	protected final JOB jobWhenTrue;
	protected final JOB jobWhenFalse;

	protected JOB currentJob;

	public SwitchJobDelegate(E maid, JOB jobWhenTrue, JOB jobWhenFalse) {
		super(maid);
		this.jobWhenTrue = jobWhenTrue;
		this.jobWhenFalse = jobWhenFalse;
		doSwitchCurrentJob();
	}

	@Override
	protected final void doUpdateJob(E maid) {
		doSwitchCurrentJob();
		currentJob.doUpdateJob();
	}

	private final void doSwitchCurrentJob() {
		if (switchJob()) {
			currentJob = jobWhenTrue;
		} else {
			currentJob = jobWhenFalse;
		}
	}

	protected abstract boolean switchJob();

}
