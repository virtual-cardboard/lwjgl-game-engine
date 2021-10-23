package context.visuals.lwjgl;

import context.GLContext;

public abstract class GLObject {

	/**
	 * Binds the {@link GLObject}.
	 * 
	 * @param glContext the {@link GLContext}
	 */
	abstract void bind(GLContext glContext);

}
