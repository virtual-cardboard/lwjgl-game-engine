package common.loader;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import common.loader.loadtask.LoadTask;

public class Loader implements Runnable {

	private static final int NUM_THREADS = 4;

	private boolean isDone = false;
	private boolean isBlockLoad = true;
	private Queue<LoadTask> loadTasks;
	private LoadTask currentTask;
	private ExecutorService newFixedThreadPool;

	public Loader() {
		loadTasks = new LinkedList<>();
		newFixedThreadPool = Executors.newFixedThreadPool(NUM_THREADS);
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
	}

	private void loadNextTask() {
		if (!loadTasks.isEmpty() && !isBlockLoad) {
			currentTask = loadTasks.poll();
			if (currentTask != null) {
				newFixedThreadPool.submit(currentTask::run);
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
