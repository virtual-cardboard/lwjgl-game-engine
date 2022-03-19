package common.loader.graph.loader;

import context.GLContext;

public interface GLLoader2Arg<T, A, B> extends ArgLoader {

	@Override
	public default int numDependencies() {
		return 2;
	}

	public T load(GLContext glContext, A a, B b);

	// LoadingScheduler g
	// VertexShaderFile v_file
	// FragmentShaderFile f_file
	// Pending<VertexShader> v_future = g.load(new VertexLoader(), v_file, "v");
	// Pending<FragmentShader> f_future = g.load(new FragmentLoader(), f_file, "f");
	// Pending<ShaderProgram> s_future = g.load(new ShaderProgramLoader(), v_future,
	// f_future, "s");
	//
	// g.loadAll(resourcePack);
	//
	// ...elsewhewre
	//
	// VertexShader v = resourcePack.get("v");

}
