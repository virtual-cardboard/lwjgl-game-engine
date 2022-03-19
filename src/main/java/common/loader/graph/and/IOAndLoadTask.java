package common.loader.graph.and;

import java.util.function.Consumer;

import common.loader.IOLoadTask;

public class IOAndLoadTask<T> implements IOLoadTask<T> {

	private IOLoadTask<T> loadTask;
	private Consumer<T> extra;

	public IOAndLoadTask(IOLoadTask<T> loadTask, Consumer<T> extra) {
		this.loadTask = loadTask;
		this.extra = extra;
	}

	@Override
	public T loadIO() {
		T result = loadTask.call();
		extra.accept(result);
		return result;
	}

}
