package common.loader.graph.loader;

import java.io.IOException;

import context.GLContext;

public interface GLLoader2Arg<T, A, B> extends ArgLoader {

	@Override
	public default int numDependencies() {
		return 2;
	}

	public T loadGL(GLContext glContext, A a, B b) throws IOException;

}
