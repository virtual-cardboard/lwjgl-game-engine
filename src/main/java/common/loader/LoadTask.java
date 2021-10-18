package common.loader;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * Use a LoadTask for I/O operations.
 * 
 * @author Jay
 * @param <T> the thing it loads
 * @see IOLoader
 *
 */
abstract class LoadTask<T> implements Callable<T> {

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