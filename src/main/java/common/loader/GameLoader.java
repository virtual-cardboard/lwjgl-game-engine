package common.loader;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class GameLoader {

	private static final int NUM_THREADS = 4;

	private ExecutorService loaders;
	private GameLinker linker;

	public GameLoader() {
		ThreadFactory daemonThreadFactory = r -> {
			Thread t = Executors.defaultThreadFactory().newThread(r);
			t.setDaemon(true);
			return t;
		};
		loaders = Executors.newFixedThreadPool(NUM_THREADS, daemonThreadFactory);
	}

	public void start(long sharedContextWindowHandle) {
		linker = new GameLinker(sharedContextWindowHandle);
		Thread openglLoaderThread = new Thread(linker);
		openglLoaderThread.start();
	}

	public void add(LoadTask t) {
		if (t instanceof GLLoadTask) {
			GLLoadTask openglLoadTask = (GLLoadTask) t;
			openglLoadTask.setLinker(linker);
		}
		loaders.execute(t);
	}

	public void terminate() {
		linker.terminate();
		loaders.shutdown();
	}

	public boolean started() {
		return linker != null;
	}

}
