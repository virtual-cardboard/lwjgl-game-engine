package common.loader.linktask;

import common.loader.loadtask.LoadTask;
import common.timestep.WindowFrameUpdater;

/**
 * Use a LinkTask when you want to use OpenGL's functions from another thread.
 * 
 * @author Jay
 * @see LoadTask
 * @see WindowFrameUpdater
 *
 */
public abstract class LinkTask implements Runnable {

	@Override
	public final void run() {
		doRun();
	}

	public abstract void doRun();

}