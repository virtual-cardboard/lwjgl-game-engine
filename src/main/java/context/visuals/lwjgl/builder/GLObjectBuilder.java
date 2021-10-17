package context.visuals.lwjgl.builder;

import context.GLContext;
import context.visuals.lwjgl.GLObject;

public abstract class GLObjectBuilder<T extends GLObject> {

	protected GLContext context;

	public GLObjectBuilder(GLContext context) {
		this.context = context;
	}

	public abstract T build();

}
