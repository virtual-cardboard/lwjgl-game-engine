package common.loader.loadtask;

import java.io.IOException;
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

	private final CountDownLatch countDownLatch;

	public LoadTask() {
		this(new CountDownLatch(1));
	}

	public LoadTask(CountDownLatch countDownLatch) {
		this.countDownLatch = countDownLatch;
	}

	@Override
	public final void run() {
		try {
			load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public abstract void load() throws IOException;

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