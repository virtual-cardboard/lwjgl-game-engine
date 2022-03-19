package common.loader.graph.loader;

import java.io.IOException;

public interface IOLoader0Arg<T> extends ArgLoader {

	@Override
	public default int numDependencies() {
		return 0;
	}

	public T loadIO() throws IOException;

}
