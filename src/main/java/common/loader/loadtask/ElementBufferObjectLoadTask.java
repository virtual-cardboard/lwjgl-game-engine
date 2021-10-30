package common.loader.loadtask;

import common.loader.GLLoadTask;
import context.GLContext;
import context.visuals.lwjgl.ElementBufferObject;

public final class ElementBufferObjectLoadTask extends GLLoadTask<ElementBufferObject> {

	private ElementBufferObject ebo;

	public ElementBufferObjectLoadTask(int[] indices) {
		this(new ElementBufferObject(indices));
	}

	public ElementBufferObjectLoadTask(ElementBufferObject ebo) {
		this.ebo = ebo;
	}

	@Override
	protected ElementBufferObject loadGL(GLContext glContext) {
		ebo.genId();
		ebo.loadData(glContext);
		return ebo;
	}

}
