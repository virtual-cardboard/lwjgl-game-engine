package common.loader.graph;

import common.loader.GLAndLoadTask;
import common.loader.GLLoadTask;
import common.loader.graph.loader.ArgLoader;
import common.loader.graph.loader.GLLoader0Arg;
import common.loader.graph.loader.GLLoader1Arg;
import common.loader.graph.loader.GLLoader2Arg;
import context.ResourcePack;
import context.visuals.lwjgl.GLObject;

public class GLPending<T extends GLObject> extends Pending<T> {

	private GLLoadTask<T> loadTask;

	private GLPending(String resourceName, GLLoadTask<T> loadTask, ArgLoader loader) {
		super(resourceName, loader.numDependencies());
	}

	public GLPending(String resourceName, GLLoader0Arg<T> loader) {
		this(resourceName, glContext -> loader.load(glContext), loader);
	}

	public <A> GLPending(String resourceName, GLLoader1Arg<T, A> loader, Pending<A> a) {
		this(resourceName, glContext -> loader.load(glContext, a.get()), loader);
	}

	public <A, B> GLPending(String resourceName, GLLoader2Arg<T, A, B> loader, Pending<A> a, Pending<B> b) {
		this(resourceName, glContext -> loader.load(glContext, a.get(), b.get()), loader);
	}

	@Override
	public GLLoadTask<T> generateLoadTask(ResourcePack pack) {
		return new GLAndLoadTask<T>(loadTask, result -> {
			this.data = result;
			pack.add(result);
		});
	}

}
