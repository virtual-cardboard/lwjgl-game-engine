package common.loader;

import java.util.function.Consumer;

public class IOAndLoadTask<T> implements IOLoadTask<T> {

	private IOLoadTask<T> loadTask;
	private Consumer<T> extra;

	public IOAndLoadTask(IOLoadTask<T> loadTask, Consumer<T> extra) {
		this.loadTask = loadTask;
		this.extra = extra;
	}

	@Override
	public T load() {
		T result = null;
		try {
			result = loadTask.call();
		} catch (Exception e) {
			e.printStackTrace();
		}
		extra.accept(result);
		return result;
	}

}
