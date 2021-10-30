package common.loader.loadtask;

import common.loader.GLLoadTask;
import context.GLContext;
import context.visuals.lwjgl.VertexBufferObject;

public final class VertexBufferObjectLoadTask extends GLLoadTask<VertexBufferObject> {

	private VertexBufferObject vbo;

	public VertexBufferObjectLoadTask(float[] indices, int dataSize) {
		this(new VertexBufferObject(indices, dataSize));
	}

	public VertexBufferObjectLoadTask(VertexBufferObject vbo) {
		this.vbo = vbo;
	}

	@Override
	protected VertexBufferObject loadGL(GLContext glContext) {
		vbo.genId();
		vbo.loadData(glContext);
		return vbo;
	}

}
