package common.loader;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public abstract class IOLoadTask<T> extends LoadTask<T> implements Callable<T> {

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

	@Override
	protected Future<T> accept(GameLoader loader) {
		return loader.submitIOLoadTask(this);
	}

	/**
	 * Loads and returns an object of type <code>T</code>, where <code>T</code> is
	 * the generic type of this IOLoadTask.
	 * 
	 * @return an object of type <code>T</code>
	 * @throws IOException when an IOException occurs
	 */
	protected abstract T load() throws IOException;

}
