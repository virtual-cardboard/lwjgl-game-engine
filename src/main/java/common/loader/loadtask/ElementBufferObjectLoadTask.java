package common.loader.loadtask;

import common.loader.GLLoadTask;
import context.GLContext;
import context.visuals.lwjgl.ElementBufferObject;

public final class ElementBufferObjectLoadTask implements GLLoadTask<ElementBufferObject> {

	private ElementBufferObject ebo;

	public ElementBufferObjectLoadTask(int[] indices) {
		this(new ElementBufferObject(indices));
	}

	public ElementBufferObjectLoadTask(ElementBufferObject ebo) {
		this.ebo = ebo;
	}

	@Override
	public ElementBufferObject loadGL(GLContext glContext) {
		ebo.genID();
		ebo.loadData(glContext);
		return ebo;
	}

}
