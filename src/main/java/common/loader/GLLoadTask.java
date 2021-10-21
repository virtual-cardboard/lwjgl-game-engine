package common.loader;

import static org.lwjgl.opengl.GL11.glFinish;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

public abstract class GLLoadTask<T> extends LoadTask<T> {

	public GLLoadTask() {
		super();
	}

	public GLLoadTask(CountDownLatch countDownLatch) {
		super(countDownLatch);
	}

	@Override
	public final T call() throws Exception {
		T t = loadGL();
		// Call glFinish to flush openGL buffered commands.
		glFinish();
		countDownLatch.countDown();
		return t;
	}

	@Override
	protected Future<T> accept(GameLoader loader) {
		return loader.submitGLLoadTask(this);
	}

	protected abstract T loadGL();

}
