package common.loader;

import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.opengl.GL.createCapabilities;

import java.util.ArrayDeque;
import java.util.Queue;

import common.loader.loadtask.OpenglLoadTask;

public class GameLoaderLinker implements Runnable {

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
				lt.countDownLatch().countDown();
			} else {
				Thread.yield();
			}
			lt = openglLoadTasks.poll();
		}
	}

	public void add(OpenglLoadTask t) {
		openglLoadTasks.add(t);
	}

	public void terminate() {
		running = false;
	}

}
