package common.loader;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

final class GLLoadTaskFutureWrapper<V> implements RunnableFuture<V> {

	private Future<V> future;
	private GLLoadTask<V> loadTask;
	private boolean cancelled;
	private GLLoader linker;

	public GLLoadTaskFutureWrapper(GLLoadTask<V> loadTask, GLLoader linker) {
		this.loadTask = loadTask;
		this.linker = linker;
	}

	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		return cancelled = true;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public boolean isDone() {
		return future != null && future.isDone();
	}

	@Override
	public V get() throws InterruptedException, ExecutionException {
		while (future == null) {
			if (cancelled) return null;
			System.out.println("Waiting for future to be set...");
			Thread.yield();
		}
		return future.get();
	}

	@Override
	public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		while (future == null) {
			if (cancelled) return null;
			System.out.println("Waiting for future to be set...");
			Thread.yield();
		}
		return future.get(timeout, unit);
	}

	@Override
	public void run() {
		try {
			if (!cancelled) loadTask.loadIO();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		// Submit to linker and wrap future
		if (!cancelled) future = linker.submit(loadTask);
	}

}
