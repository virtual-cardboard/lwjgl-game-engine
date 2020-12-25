package common.loader;

import java.util.LinkedList;
import java.util.Queue;

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
				currentTask.loadable.load();
				currentTask.isLoaded = true;
			}
		}
	}

	private void sleepForAWhile() {
		try {
			Thread.sleep(500);
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

	public int getNumberOfTasks() {
		return loadQueue.size();
	}

	public LoadTask getCurrentTask() {
		return currentTask;
	}

}
