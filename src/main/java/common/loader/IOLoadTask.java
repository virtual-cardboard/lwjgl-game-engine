package common.loader;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public abstract class IOLoadTask<T> extends LoadTask<T> {

	public IOLoadTask() {
		super();
	}

	public IOLoadTask(CountDownLatch countDownLatch) {
		super(countDownLatch);
	}
//
//	@Override
//	public final void run() {
//	}

	@Override
	public final T call() throws Exception {
		T t = null;
		try {
			t = load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		countDownLatch.countDown();
		return t;
	}

	protected abstract T load() throws IOException;

}
