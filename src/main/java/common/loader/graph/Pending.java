package common.loader.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import common.loader.GameLoader;
import common.loader.LoadTask;
import context.ResourcePack;

public abstract class Pending<T> implements Supplier<T> {

	protected volatile int remainingDependencies;
	protected String resourceName;
	protected T data;

	protected List<Pending<?>> dependents = new ArrayList<>();
	private AtomicInteger progressBar;

	public Pending(String resourceName, AtomicInteger progressBar, int numDependencies) {
		this.resourceName = resourceName;
		this.progressBar = progressBar;
		this.remainingDependencies = numDependencies;
	}

	@Override
	public T get() {
		return data;
	}

	public boolean readyToLoad() {
		return remainingDependencies == 0;
	}

	public abstract LoadTask<T> generateLoadTask(GameLoader loader, ResourcePack pack);

	protected void incrementProgressBar() {
		progressBar.incrementAndGet();
	}

	protected void loadDependentsIfReady(GameLoader loader, ResourcePack pack) {
		for (int i = 0, m = dependents.size(); i < m; i++) {
			Pending<?> dependent = dependents.get(i);
			synchronized (dependent) {
				dependent.remainingDependencies--;
				if (dependent.readyToLoad()) {
					loader.submit(dependent.generateLoadTask(loader, pack));
				}
			}
		}
	}

}
