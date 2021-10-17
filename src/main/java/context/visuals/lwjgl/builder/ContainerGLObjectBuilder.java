package context.visuals.lwjgl.builder;

import context.GLContext;
import context.visuals.lwjgl.ContainerGLObject;

public abstract class ContainerGLObjectBuilder<T extends ContainerGLObject> extends GLObjectBuilder<T> {

	public ContainerGLObjectBuilder(GLContext context) {
		super(context);
	}

}
