package common.loader.graph;

import java.util.concurrent.atomic.AtomicInteger;

import common.loader.GLLoadTask;
import common.loader.GameLoader;
import common.loader.graph.and.GLAndLoadTask;
import common.loader.graph.loader.ArgLoader;
import context.ResourcePack;
import context.visuals.lwjgl.GLObject;

public class GLPending<T extends GLObject> extends Pending<T> {

	private GLLoadTask<T> loadTask;

	public GLPending(String resourceName, AtomicInteger progressBar, GLLoadTask<T> loadTask, ArgLoader loader) {
		super(resourceName, progressBar, loader.numDependencies());
		this.loadTask = loadTask;
	}

	@Override
	public GLLoadTask<T> generateLoadTask(GameLoader loader, ResourcePack pack) {
		return new GLAndLoadTask<>(loadTask, result -> {
			incrementProgressBar();
			this.data = result;
			pack.put(resourceName, result);
			loadDependentsIfReady(loader, pack);
		});
	}

}
