package common.loader.graph;

import common.loader.IOAndLoadTask;
import common.loader.IOLoadTask;
import common.loader.graph.loader.ArgLoader;
import common.loader.graph.loader.IOLoader0Arg;
import common.loader.graph.loader.IOLoader1Arg;
import common.loader.graph.loader.IOLoader2Arg;
import context.ResourcePack;

public class IOPending<T> extends Pending<T> {

	private IOLoadTask<T> loadTask;

	private IOPending(String resourceName, IOLoadTask<T> loadTask, ArgLoader loader) {
		super(resourceName, loader.numDependencies());
	}

	public IOPending(String resourceName, IOLoader0Arg<T> loader) {
		this(resourceName, () -> loader.load(), loader);
	}

	public <A> IOPending(String resourceName, IOLoader1Arg<T, A> loader, Pending<A> a) {
		this(resourceName, () -> loader.load(a.get()), loader);
	}

	public <A, B> IOPending(String resourceName, IOLoader2Arg<T, A, B> loader, Pending<A> a, Pending<B> b) {
		this(resourceName, () -> loader.load(a.get(), b.get()), loader);
	}

	@Override
	public IOLoadTask<T> generateLoadTask(ResourcePack pack) {
		return new IOAndLoadTask<T>(loadTask, result -> this.data = result);
	}
}
