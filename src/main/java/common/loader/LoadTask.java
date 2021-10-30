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

	protected CountDownLatch countDownLatch;

	public LoadTask() {
		this.countDownLatch = new CountDownLatch(1);
	}

	public final CountDownLatch countDownLatch() {
		return countDownLatch;
	}

	public final void setCountDownLatch(CountDownLatch countDownLatch) {
		this.countDownLatch = countDownLatch;
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