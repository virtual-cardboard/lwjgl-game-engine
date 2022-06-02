package engine.common.loader.graph;

import java.util.concurrent.atomic.AtomicInteger;

import context.ResourcePack;
import context.visuals.lwjgl.GLObject;
import engine.common.loader.GLLoadTask;
import engine.common.loader.GameLoader;
import engine.common.loader.graph.and.GLAndLoadTask;
import engine.common.loader.graph.loader.GLLoader0Arg;
import engine.common.loader.graph.loader.GLLoader1Arg;
import engine.common.loader.graph.loader.GLLoader2Arg;
import engine.common.loader.graph.loader.GLLoader3Arg;

public class GLPending<T extends GLObject> extends Pending<T> {

	private GLLoadTask<T> loadTask;

	private GLPending(String resourceName, AtomicInteger progressBar, GLLoadTask<T> loadTask, Pending<?>... dependencies) {
		super(resourceName, progressBar, dependencies);
		this.loadTask = loadTask;
	}

	public GLPending(String resourceName, AtomicInteger progressBar, GLLoader0Arg<T> loader) {
		this(resourceName, progressBar, glContext -> loader.loadGL(glContext), new Pending<?>[0]);
	}

	public <A> GLPending(String resourceName, AtomicInteger progressBar, GLLoader1Arg<T, A> loader, Pending<A> a) {
		this(resourceName, progressBar, glContext -> loader.loadGL(glContext, a.get()), a);
	}

	public <A, B> GLPending(String resourceName, AtomicInteger progressBar, GLLoader2Arg<T, A, B> loader, Pending<A> a, Pending<B> b) {
		this(resourceName, progressBar, glContext -> loader.loadGL(glContext, a.get(), b.get()), a, b);
	}

	public <A, B, C> GLPending(String resourceName, AtomicInteger progressBar, GLLoader3Arg<T, A, B, C> loader, Pending<A> a, Pending<B> b, Pending<C> c) {
		this(resourceName, progressBar, glContext -> loader.loadGL(glContext, a.get(), b.get(), c.get()), a, b, c);
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
