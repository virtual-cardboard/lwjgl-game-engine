package engine.common.loader.graph.loader;

import java.io.IOException;

public interface IOLoader3Arg<T, A, B, C> extends ArgLoader {

	@Override
	public default int numDependencies() {
		return 3;
	}

	public T loadIO(A a, B b, C c) throws IOException;

}
