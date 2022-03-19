package common.loader.graph.loader;

import context.GLContext;

public interface GLLoader1Arg<T, A> extends ArgLoader {

	@Override
	public default int numDependencies() {
		return 1;
	}

	public T load(GLContext context, A a);
}
