package context.visuals.lwjgl;

import context.GLContext;

public abstract class GLObject {

	protected GLContext context;

	public GLObject(GLContext context) {
		this.context = context;
	}

}
