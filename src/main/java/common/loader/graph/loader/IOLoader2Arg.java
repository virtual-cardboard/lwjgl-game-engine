package common.loader.graph.loader;

public interface IOLoader2Arg<T, A, B> extends ArgLoader {

	@Override
	public default int numDependencies() {
		return 2;
	}

	public T loadIO(A a, B b);

}
