package common.loader;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import common.loader.graph.loader.IOLoader0Arg;

@FunctionalInterface
public interface IOLoadTask<T> extends LoadTask<T>, Callable<T>, IOLoader0Arg<T> {

	@Override
	public default T call() {
		T t = null;
		try {
			t = loadIO();
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
	@Override
	public T loadIO() throws IOException;

}
