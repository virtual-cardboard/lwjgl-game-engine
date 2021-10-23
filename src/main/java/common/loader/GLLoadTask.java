package common.loader;

import static org.lwjgl.opengl.GL11.glFinish;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

import context.GLContext;

public abstract class GLLoadTask<T> extends LoadTask<T> {

	public GLLoadTask() {
		super();
	}

	public GLLoadTask(CountDownLatch countDownLatch) {
		super(countDownLatch);
	}

	public T doLoadGL(GLContext glContext) {
		T t = loadGL(glContext);
		glFinish();
		countDownLatch.countDown();
		return t;
	}

	@Override
	protected Future<T> accept(GameLoader loader) {
		return loader.submitGLLoadTask(this);
	}

	protected abstract T loadGL(GLContext glContext);

}
