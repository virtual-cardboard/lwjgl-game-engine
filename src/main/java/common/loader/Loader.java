package common.loader;

import java.util.LinkedList;
import java.util.Queue;

import common.loader.loadtask.LoadTask;

public class Loader implements Runnable {

	private boolean isDone = false;
	private boolean isBlockLoad = true;

	private Queue<LoadTask> loadQueue;
	private LoadTask currentTask;

	public Loader() {
		loadQueue = new LinkedList<>();
	}

	public void addLoadTask(LoadTask task) {
		loadQueue.add(task);
	}

	@Override
	public void run() {
		while (!isDone) {
			loadNextTask();
			if (loadQueue.isEmpty()) {
				sleepForAWhile();
			}
		}
	}

	private void loadNextTask() {
		if (!loadQueue.isEmpty() && !isBlockLoad) {
			currentTask = loadQueue.poll();
			if (currentTask != null) {
				currentTask.run();
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

	public Queue<LoadTask> getLoadQueue() {
		return loadQueue;
	}

	public int getNumberOfTasks() {
		return loadQueue.size();
	}

	public LoadTask getCurrentTask() {
		return currentTask;
	}

}
