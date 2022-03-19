package common.loader;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public interface IOLoadTask<T> extends LoadTask<T>, Callable<T> {

	@Override
	public default T call() throws Exception {
		T t = null;
		try {
			t = load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return t;
	}

	@Override
	public default Future<T> accept(GameLoader loader) {
		return loader.submitIOLoadTask(this);
	}

	/**
	 * Loads and returns an object of type <code>T</code>, where <code>T</code> is
	 * the generic type of this IOLoadTask.
	 * 
	 * @return an object of type <code>T</code>
	 * @throws IOException when an IOException occurs
	 */
	public T load() throws IOException;

}
