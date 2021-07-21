package common.loader.loadtask;

public abstract class LoadTask implements Runnable {

	private boolean done = false;

	@Override
	public void run() {
		doRun();
		done = true;
	}

	public abstract void doRun();

	public final boolean isDone() {
		return done;
	}

}
