package common.loader;

import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.opengl.GL.createCapabilities;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * A loader thread used for OpenGL function calls. Uses a shared context with
 * the rendering thread so that all non-container objects can be directly used
 * in the rendering thread.
 * 
 * @author Jay
 *
 */
public final class GameLinker implements Runnable {

	private boolean isDone = false;
	private Queue<GLLoadTask> glLoadTasks = new ArrayDeque<>();
	private long sharedContextWindowHandle;

	public GameLinker(long sharedContextWindowHandle) {
		this.sharedContextWindowHandle = sharedContextWindowHandle;
	}

	@Override
	public void run() {
		// Share openGL context with rendering thread.
		glfwMakeContextCurrent(sharedContextWindowHandle);
		createCapabilities();
		while (!isDone) {
			GLLoadTask task;
			while ((task = glLoadTasks.poll()) != null) {
				task.doLoadGL();
			}
			Thread.yield();
		}
	}

	/**
	 * Adds an {@link GLLoadTask} to a queue. The load task would be executed some
	 * time in the future. Added load tasks would be executed in the order that they
	 * are added.
	 * 
	 * @param t the <code>GLLoadTask</code> to add
	 */
	public void add(GLLoadTask t) {
		glLoadTasks.add(t);
	}

	/**
	 * Stops the loader linker.
	 */
	public void terminate() {
		isDone = true;
	}

}
