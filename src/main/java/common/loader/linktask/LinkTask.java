package common.loader.linktask;

/**
 * Use a LinkTask when you want to use OpenGL's functions from another thread.
 * 
 * @author Jay
 *
 */
public abstract class LinkTask implements Runnable {

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
