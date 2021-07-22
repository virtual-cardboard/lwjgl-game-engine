package common.loader.loadtask;

import java.util.concurrent.CountDownLatch;

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

	private CountDownLatch countDownLatch;

	public LoadTask() {
		this(new CountDownLatch(1));
	}

	public LoadTask(CountDownLatch countDownLatch) {
		this.countDownLatch = countDownLatch;
	}

	@Override
	public final void run() {
		countDownLatch.countDown();
		doRun();
	}

	public abstract void doRun();

	public final long getCount() {
		return countDownLatch.getCount();
	}

	public final boolean isDone() {
		return countDownLatch.getCount() == 0;
	}

	public final CountDownLatch getCountDownLatch() {
		return countDownLatch;
	}

}