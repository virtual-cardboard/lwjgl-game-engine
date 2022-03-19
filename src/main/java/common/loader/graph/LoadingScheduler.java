package common.loader.graph;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import common.loader.GameLoader;
import common.loader.graph.loader.*;
import context.ResourcePack;
import context.visuals.lwjgl.GLObject;

public class LoadingScheduler {

	private int totalTasks = 0;
	private AtomicInteger completedTasks = new AtomicInteger();

	private List<Pending<?>> rootNodes;

	public void loadInto(GameLoader loader, ResourcePack pack) {
		for (Pending<?> node : rootNodes) {
			loader.submit(node.generateLoadTask(loader, pack));
		}
	}

	public int getTotalTasks() {
		return totalTasks;
	}

	public int getCompletedTasks() {
		return completedTasks.get();
	}

	public <T> Pending<T> queue(String resourceName, IOLoader0Arg<T> loader) {
		Pending<T> pending = new IOPending<>(resourceName, completedTasks, () -> loader.loadIO(), loader);
		rootNodes.add(pending);
		totalTasks++;
		return pending;
	}

	public <T, A> Pending<T> queue(String resourceName, IOLoader1Arg<T, A> loader, Pending<A> a) {
		Pending<T> pending = new IOPending<>(resourceName, completedTasks, () -> loader.loadIO(a.get()), loader);
		totalTasks++;
		return pending;
	}

	public <T, A, B> Pending<T> queue(String resourceName, IOLoader2Arg<T, A, B> loader, Pending<A> a, Pending<B> b) {
		Pending<T> pending = new IOPending<>(resourceName, completedTasks, () -> loader.loadIO(a.get(), b.get()), loader);
		totalTasks++;
		return pending;
	}

	public <T extends GLObject> Pending<T> queue(String resourceName, GLLoader0Arg<T> loader) {
		Pending<T> pending = new GLPending<>(resourceName, completedTasks, glContext -> loader.loadGL(glContext), loader);
		rootNodes.add(pending);
		totalTasks++;
		return pending;
	}

	public <T extends GLObject, A> Pending<T> queue(String resourceName, GLLoader1Arg<T, A> loader, Pending<A> a) {
		Pending<T> pending = new GLPending<>(resourceName, completedTasks, glContext -> loader.loadGL(glContext, a.get()), loader);
		totalTasks++;
		return pending;
	}

	public <T extends GLObject, A, B> Pending<T> queue(String resourceName, GLLoader2Arg<T, A, B> loader, Pending<A> a, Pending<B> b) {
		Pending<T> pending = new GLPending<>(resourceName, completedTasks, glContext -> loader.loadGL(glContext, a.get(), b.get()), loader);
		totalTasks++;
		return pending;
	}

}
