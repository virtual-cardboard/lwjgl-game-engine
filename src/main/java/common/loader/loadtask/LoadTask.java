package common.loader.loadtask;

import common.loader.Loader;
import common.loader.linktask.LinkTask;

/**
 * Use a LoadTask for I/O operations.
 * 
 * @author Jay
 * @see LinkTask
 * @see Loader
 *
 */
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