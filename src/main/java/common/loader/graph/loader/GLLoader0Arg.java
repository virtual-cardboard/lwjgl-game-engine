package common.loader.graph.loader;

import java.io.IOException;

import context.GLContext;

public interface GLLoader0Arg<T> extends ArgLoader {

	@Override
	public default int numDependencies() {
		return 0;
	}

	public T loadGL(GLContext glContext) throws IOException;

}
