package common.loader;

import static org.lwjgl.opengl.GL11.glFinish;

import java.util.concurrent.Future;

import context.GLContext;

public interface GLLoadTask<T> extends LoadTask<T> {

	public default T doLoadGL(GLContext glContext) {
		T t = loadGL(glContext);
		glFinish(); // Executes all queued up GL commands
		return t;
	}

	@Override
	public default Future<T> accept(GameLoader loader) {
		return loader.submitGLLoadTask(this);
	}

	public T loadGL(GLContext glContext);

}
