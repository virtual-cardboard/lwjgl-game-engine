package engine.common.loader;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

public final class IOLoader {

	private static final int NUM_THREADS = 4;
	private ExecutorService loaders;

	public IOLoader() {
		ThreadFactory daemonThreadFactory = r -> {
			Thread t = Executors.defaultThreadFactory().newThread(r);
			t.setDaemon(true);
			return t;
		};
		loaders = Executors.newFixedThreadPool(NUM_THREADS, daemonThreadFactory);
	}

	public <T> Future<T> submit(IOLoadTask<T> t) {
		return loaders.submit(t);
	}

	public void terminate() {
		loaders.shutdown();
	}

}
