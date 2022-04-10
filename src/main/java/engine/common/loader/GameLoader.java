package engine.common.loader;

import static java.util.concurrent.CompletableFuture.completedFuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import context.GLContext;

public final class GameLoader {

	private IOLoader ioLoader;
	private GLLoader glLoader;
	private GLContext renderingThreadGLContext;

	public GameLoader(long shareContextWindowHandle, GLContext renderingThreadGLContext) {
		ioLoader = new IOLoader();
		glLoader = new GLLoader(shareContextWindowHandle);
		this.renderingThreadGLContext = renderingThreadGLContext;
	}

	public <T> Future<T> submit(LoadTask<T> t) {
		return t.accept(this);
	}

	public <T> T submitAndGet(LoadTask<T> t) {
		try {
			return t.accept(this).get();
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
	}

	<T> Future<T> submitIOLoadTask(IOLoadTask<T> ioLoadTask) {
		return ioLoader.submit(ioLoadTask);
	}

	<T> Future<T> submitGLLoadTask(GLLoadTask<T> glLoadTask) {
		return glLoader.submit(glLoadTask);
	}

	<T> Future<T> submitAndReturnImmediately(GLLoadTask<T> t) {
		try {
			return completedFuture(t.call(renderingThreadGLContext));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void terminate() {
		ioLoader.terminate();
		glLoader.terminate();
	}

}
