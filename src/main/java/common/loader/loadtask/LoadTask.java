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

	@Override
	public final void run() {
		doRun();
	}

	public abstract void doRun();

}