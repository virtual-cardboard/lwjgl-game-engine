package engine.common.loader.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import context.ResourcePack;
import engine.common.loader.GameLoader;
import engine.common.loader.LoadTask;

public abstract class Pending<T> implements Supplier<T> {

	protected volatile int remainingDependencies;
	protected String resourceName;
	protected T data;

	protected List<Pending<?>> dependents = new ArrayList<>();
	private AtomicInteger progressBar;

	public Pending(String resourceName, AtomicInteger progressBar, Pending<?>[] dependencies) {
		this.resourceName = resourceName;
		this.progressBar = progressBar;
		this.remainingDependencies = dependencies.length;
		for (Pending<?> dependency : dependencies) {
			dependency.dependents.add(this);
		}
	}

	@Override
	public T get() {
		return data;
	}

	public int numDependents() {
		return dependents.size();
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
