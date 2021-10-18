package context.visuals.lwjgl.builder;

import java.util.concurrent.Future;

import context.GLContext;
import context.visuals.lwjgl.GLObject;

public abstract class GLObjectBuilder<T extends GLObject> {

	protected GLContext context;

	public GLObjectBuilder(GLContext context) {
		this.context = context;
	}

	protected abstract Future<T> build(GLObjectFactory glFactory);

}
