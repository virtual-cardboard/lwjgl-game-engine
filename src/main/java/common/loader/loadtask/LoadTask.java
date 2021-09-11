package common.loader.loadtask;

import java.io.IOException;

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
		try {
			doRun();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public abstract void doRun() throws IOException;

}