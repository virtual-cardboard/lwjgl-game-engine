package common.loader;

import java.util.concurrent.Future;

public final class GameLoader {

	private IOLoader ioLoader;
	private GLLoader glLoader;

	public GameLoader(long shareContextWindowHandle) {
		ioLoader = new IOLoader();
		glLoader = new GLLoader(shareContextWindowHandle);
	}

	public <T> Future<T> submit(LoadTask<T> t) {
		if (t instanceof GLLoadTask) {
			GLLoadTask<T> gllt = (GLLoadTask<T>) t;
			GLLoadTaskFutureWrapper<T> futureWrapper = new GLLoadTaskFutureWrapper<>(gllt, glLoader);
			ioLoader.execute(futureWrapper);
			return futureWrapper;
		} else {
			return ioLoader.submit(t);
		}
	}

	public void terminate() {
		ioLoader.terminate();
		glLoader.terminate();
	}

}
