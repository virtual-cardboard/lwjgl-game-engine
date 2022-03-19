package common.loader.graph.loader;

import context.GLContext;

public interface GLLoader0Arg<T> extends ArgLoader {

	@Override
	public default int numDependencies() {
		return 0;
	}

	public T load(GLContext glContext);

}
