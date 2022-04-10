package engine.common.loader.loadtask;

import context.GLContext;
import context.visuals.lwjgl.VertexBufferObject;
import engine.common.loader.GLLoadTask;

public final class VertexBufferObjectLoadTask implements GLLoadTask<VertexBufferObject> {

	private VertexBufferObject vbo;

	public VertexBufferObjectLoadTask(float[] indices, int dataSize) {
		this(new VertexBufferObject(indices, dataSize));
	}

	public VertexBufferObjectLoadTask(VertexBufferObject vbo) {
		this.vbo = vbo;
	}

	@Override
	public VertexBufferObject loadGL(GLContext glContext) {
		vbo.genID();
		vbo.loadData(glContext);
		return vbo;
	}

}
