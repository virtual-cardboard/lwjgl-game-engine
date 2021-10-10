package common.loader;

import java.util.concurrent.CountDownLatch;

/**
 * Use a LoadTask for I/O operations.
 * 
 * @author Jay
 * @see GameLoader
 *
 */
abstract class LoadTask implements Runnable {

	protected final CountDownLatch countDownLatch;

	public LoadTask() {
		this(new CountDownLatch(1));
	}

	public LoadTask(CountDownLatch countDownLatch) {
		this.countDownLatch = countDownLatch;
	}

	public final CountDownLatch countDownLatch() {
		return countDownLatch;
	}

	public final void await() {
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}