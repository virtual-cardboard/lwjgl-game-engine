package common.loader;

import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;

import common.loader.loadtask.LoadTask;

public class Loader implements Runnable {

	private static final int NUM_THREADS = 4;

	private boolean isDone = false;
	private boolean isBlockLoad = false;
	private Queue<LoadTask> loadTasks;
	private LoadTask currentTask;
	private ExecutorService fixedThreadPool;

	public Loader() {
		loadTasks = new LinkedBlockingQueue<>();
		fixedThreadPool = Executors.newFixedThreadPool(NUM_THREADS,
				new ThreadFactory() {
					@Override
					public Thread newThread(Runnable r) {
						Thread t = Executors.defaultThreadFactory().newThread(r);
						t.setDaemon(true);
						return t;
					}
				});
	}

	public void addLoadTask(LoadTask task) {
		loadTasks.add(task);
	}

	@Override
	public void run() {
		while (!isDone) {
			loadNextTask();
			if (loadTasks.isEmpty()) {
				sleepForAWhile();
			}
		}
		fixedThreadPool.shutdown();
	}

	private void loadNextTask() {
		if (!loadTasks.isEmpty() && !isBlockLoad) {
			currentTask = loadTasks.poll();
			if (currentTask != null) {
				// Use ExecutorService#execute() rather than ExecutorService#submit() because
				// execute will terminate the thread if an exception is thrown. See more here:
				// https://stackoverflow.com/questions/18730290/what-is-the-difference-between-executorservice-submit-and-executorservice-execut
				fixedThreadPool.execute(currentTask::run);
			}
		}
	}

	private void sleepForAWhile() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void blockLoading() {
		isBlockLoad = true;
	}

	public void unBlockLoading() {
		isBlockLoad = false;
	}

	public void flagIsDone() {
		isDone = true;
	}

	public Queue<LoadTask> getLoadTasks() {
		return loadTasks;
	}

	public int getNumberOfTasks() {
		return loadTasks.size();
	}

	public LoadTask getCurrentTask() {
		return currentTask;
	}

}
