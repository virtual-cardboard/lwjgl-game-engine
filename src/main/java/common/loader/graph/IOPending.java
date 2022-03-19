package common.loader.graph;

import java.util.concurrent.atomic.AtomicInteger;

import common.loader.GameLoader;
import common.loader.IOLoadTask;
import common.loader.graph.and.IOAndLoadTask;
import common.loader.graph.loader.ArgLoader;
import context.ResourcePack;

public class IOPending<T> extends Pending<T> {

	private IOLoadTask<T> loadTask;

	public IOPending(String resourceName, AtomicInteger progressBar, IOLoadTask<T> loadTask, ArgLoader loader) {
		super(resourceName, progressBar, loader.numDependencies());
		this.loadTask = loadTask;
	}

	@Override
	public IOLoadTask<T> generateLoadTask(GameLoader loader, ResourcePack pack) {
		return new IOAndLoadTask<>(loadTask, result -> {
			incrementProgressBar();
			this.data = result;
			loadDependentsIfReady(loader, pack);
		});
	}
}
