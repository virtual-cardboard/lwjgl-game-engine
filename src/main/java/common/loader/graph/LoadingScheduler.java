package common.loader.graph;

import java.util.List;

import common.loader.GameLoader;
import common.loader.graph.loader.GLLoader0Arg;
import common.loader.graph.loader.GLLoader1Arg;
import common.loader.graph.loader.GLLoader2Arg;
import common.loader.graph.loader.IOLoader0Arg;
import common.loader.graph.loader.IOLoader1Arg;
import common.loader.graph.loader.IOLoader2Arg;
import context.ResourcePack;

public class LoadingScheduler {

	private GameLoader loader;

	@SuppressWarnings("rawtypes")
	private List<Pending> rootNodes;

	public LoadingScheduler(GameLoader loader) {
		this.loader = loader;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void loadInto(ResourcePack pack) {
		for (Pending node : rootNodes) {
			loader.submit(node.generateLoadTask(pack));
		}
	}

	public <T> Pending<T> queue(String resourceName, IOLoader0Arg<T> loader) {
		Pending<T> pending = new IOPending<T>(resourceName, loader);
		rootNodes.add(pending);
		return pending;
	}

	public <T, A> Pending<T> queue(String resourceName, IOLoader1Arg<T, A> loader, Pending<A> a) {
		Pending<T> pending = new IOPending<T>(resourceName, loader, a);
		rootNodes.add(pending);
		return pending;
	}

	public <T, A, B> Pending<T> queue(String resourceName, IOLoader2Arg<T, A, B> loader, Pending<A> a, Pending<B> b) {
		Pending<T> pending = new IOPending<T>(resourceName, loader, a, b);
		rootNodes.add(pending);
		return pending;
	}

	public <T> Pending<T> queue(String resourceName, GLLoader0Arg<T> loader) {
		Pending<T> pending = new GLPending<T>(resourceName, loader);
		rootNodes.add(pending);
		return pending;
	}

	public <T, A> Pending<T> queue(String resourceName, GLLoader1Arg<T, A> loader, Pending<A> a) {
		Pending<T> pending = new GLPending<T>(resourceName, loader, a);
		rootNodes.add(pending);
		return pending;
	}

	public <T, A, B> Pending<T> queue(String resourceName, GLLoader2Arg<T, A, B> loader, Pending<A> a, Pending<B> b) {
		Pending<T> pending = new GLPending<T>(resourceName, loader, a, b);
		rootNodes.add(pending);
		return pending;
	}

}
