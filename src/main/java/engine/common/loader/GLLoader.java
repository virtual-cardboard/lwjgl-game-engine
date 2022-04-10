package engine.common.loader;

import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.opengl.GL.createCapabilities;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import context.GLContext;

/**
 * A loader thread used for OpenGL function calls. Uses a shared context with
 * the rendering thread so that all non-container objects can be directly used
 * in the rendering thread.
 * 
 * @author Jay
 *
 */
public final class GLLoader {

	private ExecutorService executor;
	private GLContext glContext;

	public GLLoader(long sharedContextWindowHandle) {
		executor = Executors.newSingleThreadExecutor(r -> {
			Thread t = Executors.defaultThreadFactory().newThread(r);
			t.setDaemon(true);
			return t;
		});
		// Share context
		executor.execute(() -> {
			glfwMakeContextCurrent(sharedContextWindowHandle);
			createCapabilities();
		});
		glContext = new GLContext();
	}

	/**
	 * Submits a {@link GLLoadTask} to the executorService. The load task would be
	 * executed some time in the future. Submitted load tasks would be executed in
	 * the order that they are added.
	 * 
	 * @param glLoadTask the <code>GLLoadTask</code> to submit
	 * @return the future of the loaded object
	 */
	public <T> Future<T> submit(GLLoadTask<T> glLoadTask) {
		return executor.submit(() -> glLoadTask.call(glContext));
	}

	public void terminate() {
		executor.shutdown();
	}

}
