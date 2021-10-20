package common.loadertest;

import java.util.ArrayList;

public class LoaderTask<T> {

	private ArrayList<Class<?>> dependencies = new ArrayList<>();

	public LoaderTask() {
	}

	@SuppressWarnings("unchecked")
	public <R> LoaderDependencyBuilder<?> builder() {
		this.dependencies = new ArrayList<>(dependencies);
		Class<R> remove = (Class<R>) dependencies.remove(0);
		System.out.println(remove.getName());
		return new LoaderDependencyBuilder<R>(dependencies);
	}

}
