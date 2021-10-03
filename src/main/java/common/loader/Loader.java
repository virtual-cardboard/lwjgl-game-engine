package common.loader;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import common.loader.loadtask.LoadTask;

public class Loader {

	private static final int NUM_THREADS = 4;

	private ExecutorService fixedThreadPool;

	public Loader() {
		fixedThreadPool = Executors.newFixedThreadPool(NUM_THREADS, new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				Thread t = Executors.defaultThreadFactory().newThread(r);
				t.setDaemon(true);
				return t;
			}
		});
	}

	public void add(LoadTask task) {
		fixedThreadPool.execute(task);
	}

	public void terminate() {
		fixedThreadPool.shutdown();
	}

}
