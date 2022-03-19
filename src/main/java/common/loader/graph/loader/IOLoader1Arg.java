package common.loader.graph.loader;

public interface IOLoader1Arg<T, A> extends ArgLoader {

	@Override
	public default int numDependencies() {
		return 1;
	}

	public T load(A a);

}
