package engine.common.loader.graph.loader;

import java.io.IOException;

import context.GLContext;

public interface GLLoader1Arg<T, A> extends ArgLoader {

	@Override
	public default int numDependencies() {
		return 1;
	}

	public T loadGL(GLContext context, A a) throws IOException;
}
