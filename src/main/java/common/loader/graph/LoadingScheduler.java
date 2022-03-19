package common.loader.graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import common.loader.GameLoader;
import common.loader.graph.loader.*;
import context.ResourcePack;
import context.visuals.lwjgl.GLObject;

/**
 * Smart dependency loading scheduler. Internally creates a dependency graph
 * such that loading can be achieved with maximum parallelism. Inspired by
 * {@link Future}s and Promises.
 * 
 * @author Lunkle
 *
 */
public class LoadingScheduler {

	/**
	 * It is necessary to split the comparator definition to two lines because
	 * otherwise the compiler cannot infer the {@link Pending} type.
	 */
	private static final Comparator<Pending<?>> DEPENDENTS_COMPARATOR = Comparator.comparingInt(Pending::numDependents);
	/**
	 * It is important to load tasks in an order such that the maximum dependents
	 * can be unlocked, so that the loader can maximize parallelism. That order is:
	 * decreasing order of dependents count.
	 */
	private static final Comparator<Pending<?>> DEPENDENTS_REVERSE_COMPARATOR = DEPENDENTS_COMPARATOR.reversed();

	private int totalTasks = 0;
	private AtomicInteger completedTasks = new AtomicInteger();

	private List<Pending<?>> tasksWithNoDependencies = new ArrayList<>();

	public void loadInto(GameLoader loader, ResourcePack pack) {
		tasksWithNoDependencies.sort(DEPENDENTS_REVERSE_COMPARATOR);
		for (Pending<?> node : tasksWithNoDependencies) {
			loader.submit(node.generateLoadTask(loader, pack));
		}
	}

	@Override
	public String toString() {
		return "Loading Scheduler [" + completedTasks() + "/" + totalTasks + "] tasks completed";
	}

	public int totalTasks() {
		return totalTasks;
	}

	public int completedTasks() {
		return completedTasks.get();
	}

	public <T> Pending<T> queue(String resourceName, IOLoader0Arg<T> loader) {
		Pending<T> pending = new IOPending<>(resourceName, completedTasks, loader);
		tasksWithNoDependencies.add(pending);
		totalTasks++;
		return pending;
	}

	public <T, A> Pending<T> queue(String resourceName, IOLoader1Arg<T, A> loader, Pending<A> a) {
		Pending<T> pending = new IOPending<>(resourceName, completedTasks, loader, a);
		totalTasks++;
		return pending;
	}

	public <T, A, B> Pending<T> queue(String resourceName, IOLoader2Arg<T, A, B> loader, Pending<A> a, Pending<B> b) {
		Pending<T> pending = new IOPending<>(resourceName, completedTasks, loader, a, b);
		totalTasks++;
		return pending;
	}

	public <T extends GLObject> Pending<T> queue(String resourceName, GLLoader0Arg<T> loader) {
		Pending<T> pending = new GLPending<>(resourceName, completedTasks, loader);
		tasksWithNoDependencies.add(pending);
		totalTasks++;
		return pending;
	}

	public <T extends GLObject, A> Pending<T> queue(String resourceName, GLLoader1Arg<T, A> loader, Pending<A> a) {
		Pending<T> pending = new GLPending<>(resourceName, completedTasks, loader, a);
		totalTasks++;
		return pending;
	}

	public <T extends GLObject, A, B> Pending<T> queue(String resourceName, GLLoader2Arg<T, A, B> loader, Pending<A> a, Pending<B> b) {
		Pending<T> pending = new GLPending<>(resourceName, completedTasks, loader, a, b);
		totalTasks++;
		return pending;
	}

}
