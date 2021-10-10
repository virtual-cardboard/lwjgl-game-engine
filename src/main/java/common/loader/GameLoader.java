package common.loader;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import common.loader.loadtask.LoadTask;
import common.loader.loadtask.OpenGLLoadTask;

public class GameLoader {

	private static final int NUM_THREADS = 4;

	private ExecutorService threadPool;
	private GameLoaderLinker loaderLinker;

	public GameLoader() {
		ThreadFactory daemonThreadFactory = r -> {
			Thread t = Executors.defaultThreadFactory().newThread(r);
			t.setDaemon(true);
			return t;
		};
		threadPool = Executors.newFixedThreadPool(NUM_THREADS, daemonThreadFactory);
	}

	public void start(long sharedContextWindowHandle) {
		loaderLinker = new GameLoaderLinker(sharedContextWindowHandle);
		Thread openglLoaderThread = new Thread(loaderLinker);
		openglLoaderThread.start();
	}

	public void add(LoadTask t) {
		threadPool.execute(t);
		if (t instanceof OpenGLLoadTask) {
			OpenGLLoadTask openglLoadTask = (OpenGLLoadTask) t;
			openglLoadTask.setLoaderLinker(loaderLinker);
		} else {
			t.countDownLatch().countDown();
		}
	}

	public void terminate() {
		loaderLinker.terminate();
		threadPool.shutdown();
	}

	public boolean started() {
		return loaderLinker != null;
	}

}
