package common.loader;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

public class IOLoader {

	private static final int NUM_THREADS = 4;

	private ExecutorService loaders;
	private GLLoader linker;

	public IOLoader() {
		ThreadFactory daemonThreadFactory = r -> {
			Thread t = Executors.defaultThreadFactory().newThread(r);
			t.setDaemon(true);
			return t;
		};
		loaders = Executors.newFixedThreadPool(NUM_THREADS, daemonThreadFactory);
	}

	public void start(long sharedContextWindowHandle) {
		linker = new GLLoader(sharedContextWindowHandle);
//		Thread openglLoaderThread = new Thread(linker);
//		openglLoaderThread.start();
	}

	public <T> Future<T> submit(LoadTask<T> t) {
		if (t instanceof GLLoadTask) {
			GLLoadTask<T> gllt = (GLLoadTask<T>) t;
			GLLoadTaskFutureWrapper<T> futureWrapper = new GLLoadTaskFutureWrapper<>(gllt, linker);
			loaders.execute(futureWrapper);
			return futureWrapper;
		} else {
			return loaders.submit(t);
		}
	}

	public void terminate() {
		linker.terminate();
		loaders.shutdown();
	}

	public boolean started() {
		return linker != null;
	}

}
