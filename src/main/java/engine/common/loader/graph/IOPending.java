package engine.common.loader.graph;

import java.util.concurrent.atomic.AtomicInteger;

import context.ResourcePack;
import context.visuals.renderer.GameRenderer;
import engine.common.loader.GameLoader;
import engine.common.loader.IOLoadTask;
import engine.common.loader.graph.and.IOAndLoadTask;
import engine.common.loader.graph.loader.IOLoader0Arg;
import engine.common.loader.graph.loader.IOLoader1Arg;
import engine.common.loader.graph.loader.IOLoader2Arg;
import engine.common.loader.graph.loader.IOLoader3Arg;

public class IOPending<T> extends Pending<T> {

	private IOLoadTask<T> loadTask;

	private IOPending(String resourceName, AtomicInteger progressBar, IOLoadTask<T> loadTask, Pending<?>... dependencies) {
		super(resourceName, progressBar, dependencies);
		this.loadTask = loadTask;
	}

	public IOPending(String resourceName, AtomicInteger progressBar, IOLoader0Arg<T> loader) {
		this(resourceName, progressBar, () -> loader.loadIO(), new Pending<?>[0]);
	}

	public <A> IOPending(String resourceName, AtomicInteger progressBar, IOLoader1Arg<T, A> loader, Pending<A> a) {
		this(resourceName, progressBar, () -> loader.loadIO(a.get()), a);
	}

	public <A, B> IOPending(String resourceName, AtomicInteger progressBar, IOLoader2Arg<T, A, B> loader, Pending<A> a, Pending<B> b) {
		this(resourceName, progressBar, () -> loader.loadIO(a.get(), b.get()), a, b);
	}

	public <A, B, C> IOPending(String resourceName, AtomicInteger progressBar, IOLoader3Arg<T, A, B, C> loader, Pending<A> a, Pending<B> b, Pending<C> c) {
		this(resourceName, progressBar, () -> loader.loadIO(a.get(), b.get(), c.get()), a, b, c);
	}

	@Override
	public IOLoadTask<T> generateLoadTask(GameLoader loader, ResourcePack pack) {
		return new IOAndLoadTask<>(loadTask, result -> {
			incrementProgressBar();
			this.data = result;
			loadDependentsIfReady(loader, pack);
			if (result instanceof GameRenderer) {
				pack.putRenderer(resourceName, (GameRenderer) result);
			}
		});
	}
}
