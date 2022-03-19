package common.loader.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.function.Supplier;

import common.loader.LoadTask;
import context.ResourcePack;

public abstract class Pending<T> implements Supplier<T> {

	protected CountDownLatch dependencyCountdown;
	protected String resourceName;
	protected T data;

	@SuppressWarnings("rawtypes")
	protected List<Pending> dependents = new ArrayList<>();

	public Pending(String resourceName, int numDependencies) {
		this.resourceName = resourceName;
		this.dependencyCountdown = new CountDownLatch(numDependencies);
	}

	@Override
	public T get() {
		return data;
	}

	public CountDownLatch dependencyCountdown() {
		return dependencyCountdown;
	}

	public abstract LoadTask<T> generateLoadTask(ResourcePack pack);

}
