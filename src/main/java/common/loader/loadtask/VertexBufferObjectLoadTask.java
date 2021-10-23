package common.loader.loadtask;

import java.util.concurrent.CountDownLatch;

import common.loader.GLLoadTask;
import context.GLContext;
import context.visuals.lwjgl.VertexBufferObject;

public final class VertexBufferObjectLoadTask extends GLLoadTask<VertexBufferObject> {

	private float[] indices;
	private int dataSize;

	public VertexBufferObjectLoadTask(float[] indices, int dataSize) {
		this(new CountDownLatch(1), indices, dataSize);
	}

	public VertexBufferObjectLoadTask(CountDownLatch countDownLatch, float[] indices, int dataSize) {
		super(countDownLatch);
		this.indices = indices;
		this.dataSize = dataSize;
	}

	@Override
	protected VertexBufferObject loadGL(GLContext glContext) {
		VertexBufferObject vbo = new VertexBufferObject(indices, dataSize);
		vbo.generateId();
		vbo.loadData(glContext);
		return vbo;
	}

}
