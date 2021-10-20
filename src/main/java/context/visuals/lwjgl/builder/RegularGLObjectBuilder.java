package context.visuals.lwjgl.builder;

import context.GLContext;
import context.visuals.lwjgl.RegularGLObject;

public abstract class RegularGLObjectBuilder<T extends RegularGLObject> extends GLObjectBuilder<T> {

	public RegularGLObjectBuilder(GLContext context) {
		super(context);
	}

}
