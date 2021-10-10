package common.loader;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public abstract class IOLoadTask extends LoadTask {

	public IOLoadTask() {
		super();
	}

	public IOLoadTask(CountDownLatch countDownLatch) {
		super(countDownLatch);
	}

	@Override
	public final void run() {
		try {
			load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		countDownLatch.countDown();
	}

	protected abstract void load() throws IOException;

}
