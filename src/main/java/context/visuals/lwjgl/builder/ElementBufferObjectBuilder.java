package context.visuals.lwjgl.builder;

import context.GLContext;
import context.visuals.lwjgl.ElementBufferObject;

public final class ElementBufferObjectBuilder extends RegularGLObjectBuilder<ElementBufferObject> {

	private int[] indices;

	public ElementBufferObjectBuilder(GLContext context, int[] indices) {
		super(context);
		this.indices = indices;
	}

	@Override
	public ElementBufferObject build() {
		ElementBufferObject ebo = new ElementBufferObject(context, indices);
		ebo.generateId();
		ebo.loadData();
		return ebo;
	}

}
