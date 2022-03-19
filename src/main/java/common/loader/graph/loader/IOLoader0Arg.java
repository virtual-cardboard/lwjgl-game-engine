package common.loader.graph.loader;

public interface IOLoader0Arg<T> extends ArgLoader {

	@Override
	public default int numDependencies() {
		return 0;
	}

	public T load();

}
