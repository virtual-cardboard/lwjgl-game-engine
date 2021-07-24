package common.loader.linktask;

import common.loader.loadtask.LoadTask;
import common.timestep.WindowFrameUpdateTimer;

/**
 * Use a LinkTask when you want to use OpenGL's functions from another thread.
 * 
 * @author Jay
 * @see LoadTask
 * @see WindowFrameUpdateTimer
 *
 */
public abstract class LinkTask implements Runnable {

	@Override
	public final void run() {
		doRun();
	}

	public abstract void doRun();

}