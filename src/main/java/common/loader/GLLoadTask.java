package common.loader;

import static org.lwjgl.opengl.GL11.glFinish;

import java.io.IOException;
import java.util.concurrent.Future;

import common.loader.graph.loader.GLLoader0Arg;
import context.GLContext;

@FunctionalInterface
public interface GLLoadTask<T> extends LoadTask<T>, GLLoader0Arg<T> {

	public default T call(GLContext glContext) {
		T t = null;
		try {
			t = loadGL(glContext);
		} catch (IOException e) {
			e.printStackTrace();
		}
		glFinish(); // Executes all queued up GL commands
		return t;
	}

	@Override
	public default Future<T> accept(GameLoader loader) {
		return loader.submitGLLoadTask(this);
	}

	@Override
	public T loadGL(GLContext glContext) throws IOException;

}
