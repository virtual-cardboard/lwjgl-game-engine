package common.loader.linktask;

import java.util.concurrent.CountDownLatch;

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

	private CountDownLatch countDownLatch;

	public LinkTask() {
		this(new CountDownLatch(1));
	}

	public LinkTask(CountDownLatch countDownLatch) {
		this.countDownLatch = countDownLatch;
	}

	@Override
	public final void run() {
		countDownLatch.countDown();
		doRun();
	}

	public abstract void doRun();

	public long getCount() {
		return countDownLatch.getCount();
	}

	public final boolean isDone() {
		return countDownLatch.getCount() == 0;
	}

	public CountDownLatch getCountDownLatch() {
		return countDownLatch;
	}
}