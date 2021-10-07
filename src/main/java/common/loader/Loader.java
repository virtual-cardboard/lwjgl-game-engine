package common.loader;

import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.opengl.GL.createCapabilities;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import common.loader.loadtask.GlLoadTask;
import common.loader.loadtask.LoadTask;
import context.GameWindow;

public class Loader {

	private static final int NUM_THREADS = 4;

	private ExecutorService openGLExecutorService;
	private ExecutorService nonOpenGLExecutorService;

	public Loader(GameWindow window, CountDownLatch windowCountDownLatch) {
		ThreadFactory daemonThreadFactory = r -> {
			Thread t = Executors.defaultThreadFactory().newThread(r);
			t.setDaemon(true);
			return t;
		};
		nonOpenGLExecutorService = Executors.newFixedThreadPool(NUM_THREADS, daemonThreadFactory);
		if (window == null) return;
		openGLExecutorService = Executors.newSingleThreadExecutor(daemonThreadFactory);
		openGLExecutorService.execute(() -> {
			try {
				windowCountDownLatch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			long sharedContextWindowHandle = window.getSharedContextWindowHandle();
			glfwMakeContextCurrent(sharedContextWindowHandle);
			createCapabilities();
		});
	}

	public void add(LoadTask t) {
		if (t instanceof GlLoadTask) ((GlLoadTask) t).setOpenGlExecutorService(openGLExecutorService);
		nonOpenGLExecutorService.execute(t);
	}

	public void terminate() {
		openGLExecutorService.shutdown();
		nonOpenGLExecutorService.shutdown();
	}

}
