package common.loader;

import static org.lwjgl.opengl.GL11.glFinish;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public abstract class GLLoadTask<T> extends LoadTask<T> {

	public GLLoadTask() {
		super();
	}

	public GLLoadTask(CountDownLatch countDownLatch) {
		super(countDownLatch);
	}

	@Override
	public final T call() throws Exception {
		loadIO();
		return null;
	}

	final T doLoadGL() {
		T t = loadGL();
		// Call glFinish to flush openGL buffered commands.
		glFinish();
		countDownLatch.countDown();
		return t;
	}

	protected abstract void loadIO() throws IOException;

	protected abstract T loadGL();

}
