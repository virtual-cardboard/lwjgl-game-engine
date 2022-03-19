package common.loader;

import java.util.function.Consumer;

import context.GLContext;

public class GLAndLoadTask<T> implements GLLoadTask<T> {

	private GLLoadTask<T> loadTask;
	private Consumer<T> extra;

	public GLAndLoadTask(GLLoadTask<T> loadTask, Consumer<T> extra) {
		this.loadTask = loadTask;
		this.extra = extra;
	}

	@Override
	public T loadGL(GLContext glContext) {
		T result = loadTask.loadGL(glContext);
		extra.accept(result);
		return result;
	}

}
