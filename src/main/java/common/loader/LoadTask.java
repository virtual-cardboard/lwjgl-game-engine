package common.loader;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

/**
 * Use a LoadTask for I/O operations.
 * 
 * @author Jay
 * @param <T> the thing it loads
 * @see IOLoader
 *
 */
abstract class LoadTask<T> {

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

	protected abstract Future<T> accept(GameLoader loader);

	public final void await() {
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}