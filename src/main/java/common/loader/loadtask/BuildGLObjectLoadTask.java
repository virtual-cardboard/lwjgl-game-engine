package common.loader.loadtask;

import java.io.IOException;

import common.loader.GLLoadTask;
import context.visuals.lwjgl.RegularGLObject;
import context.visuals.lwjgl.builder.RegularGLObjectBuilder;

public final class BuildGLObjectLoadTask<T extends RegularGLObject> extends GLLoadTask {

	private RegularGLObjectBuilder<T> builder;
	private T glObject;

	public BuildGLObjectLoadTask(RegularGLObjectBuilder<T> builder) {
		this.builder = builder;
	}

	@Override
	protected void loadIO() throws IOException {

	}

	@Override
	protected void loadGL() {
		glObject = builder.build();
	}

	public T getGlObject() {
		return glObject;
	}

}
