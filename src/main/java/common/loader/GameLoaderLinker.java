package common.loader;

import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL11.glFinish;

import java.util.ArrayDeque;
import java.util.Queue;

import common.loader.loadtask.OpenglLoadTask;

/**
 * A loader thread used for OpenGL function calls. Uses a shared context with
 * the rendering thread so that all non-container objects can be directly used
 * in the rendering thread.
 * 
 * @author Jay
 *
 */
public final class GameLoaderLinker implements Runnable {

	private boolean running = true;
	private Queue<OpenglLoadTask> openglLoadTasks = new ArrayDeque<>();
	private long sharedContextWindowHandle;

	public GameLoaderLinker(long sharedContextWindowHandle) {
		this.sharedContextWindowHandle = sharedContextWindowHandle;
	}

	@Override
	public void run() {
		glfwMakeContextCurrent(sharedContextWindowHandle);
		createCapabilities();
		OpenglLoadTask lt = openglLoadTasks.poll();
		while (running) {
			if (lt != null) {
				lt.loadOpengl();
				// Must call glFinish, or else the loader thread buffer this command until the
				// next glSwapBuffers() call - which would never happen.
				glFinish();
				lt.countDownLatch().countDown();
			} else {
				Thread.yield();
			}
			lt = openglLoadTasks.poll();
		}
	}

	/**
	 * Adds an {@link OpenglLoadTask} to a queue. The load task would be executed
	 * some time in the future. Added load tasks would be executed in the order that
	 * they are added.
	 * 
	 * @param t the <code>OpenglLoadTask</code> to add
	 */
	public void add(OpenglLoadTask t) {
		openglLoadTasks.add(t);
	}

	/**
	 * Stops the loader linker.
	 */
	public void terminate() {
		running = false;
	}

}
