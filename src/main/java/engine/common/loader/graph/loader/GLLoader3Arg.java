package engine.common.loader.graph.loader;

import java.io.IOException;

import context.GLContext;

public interface GLLoader3Arg<T, A, B, C> extends ArgLoader {

	@Override
	public default int numDependencies() {
		return 3;
	}

	public T loadGL(GLContext glContext, A a, B b, C c) throws IOException;

}
