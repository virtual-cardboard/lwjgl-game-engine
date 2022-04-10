package engine.common.loader.graph.loader;

import java.io.IOException;

public interface IOLoader1Arg<T, A> extends ArgLoader {

	@Override
	public default int numDependencies() {
		return 1;
	}

	public T loadIO(A a) throws IOException;

}
