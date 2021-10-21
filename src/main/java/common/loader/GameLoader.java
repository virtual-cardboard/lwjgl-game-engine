package common.loader;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public final class GameLoader {

	private IOLoader ioLoader;
	private GLLoader glLoader;

	public GameLoader(long shareContextWindowHandle) {
		ioLoader = new IOLoader();
		glLoader = new GLLoader(shareContextWindowHandle);
	}

	public <T> Future<T> submit(LoadTask<T> t) {
		return t.accept(this);
	}

	<T> Future<T> submitIOLoadTask(IOLoadTask<T> ioLoadTask) {
		return ioLoader.submit(ioLoadTask);
	}

	<T> Future<T> submitGLLoadTask(GLLoadTask<T> glLoadTask) {
		return glLoader.submit(glLoadTask);
	}

	<T> Future<T> submitAndReturnImmediately(LoadTask<T> t) {
		try {
			return CompletableFuture.completedFuture(t.call());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void terminate() {
		ioLoader.terminate();
		glLoader.terminate();
	}

}
