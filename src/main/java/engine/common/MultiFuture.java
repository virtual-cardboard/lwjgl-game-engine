package engine.common;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public final class MultiFuture<T> implements Future<T> {

	private Future<T> f;
	private Future<?>[] fs;

	public MultiFuture(Future<T> f, Future<?>... fs) {
		this.f = f;
		this.fs = fs;
	}

	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		for (int i = 0; i < fs.length; i++) {
			fs[i].cancel(mayInterruptIfRunning);
		}
		return f.cancel(mayInterruptIfRunning);
	}

	@Override
	public boolean isCancelled() {
		return f.isCancelled();
	}

	@Override
	public boolean isDone() {
		if (!f.isDone()) return false;
		for (int i = 0; i < fs.length; i++) {
			if (!fs[i].isDone()) return false;
		}
		return true;
	}

	@Override
	public T get() {
		try {
			for (int i = 0; i < fs.length; i++) {
				fs[i].get();
			}
			return f.get();
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		throw new RuntimeException("Invalid function for MultiFuture.");
	}

}
