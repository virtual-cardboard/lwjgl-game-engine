package context.visuals.lwjgl.builder;

import java.util.concurrent.Future;

import context.GLContext;
import context.visuals.lwjgl.ElementBufferObject;

public final class ElementBufferObjectBuilder extends RegularGLObjectBuilder<ElementBufferObject> {

	private int[] indices;

	public ElementBufferObjectBuilder(GLContext context, int[] indices) {
		super(context);
		this.indices = indices;
	}

	@Override
	protected Future<ElementBufferObject> build(GLObjectFactory glFactory) {
		return glFactory.build(this);
	}

	int[] getIndices() {
		return indices;
	}

}
