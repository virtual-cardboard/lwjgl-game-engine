package common.loader.loadtask;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import common.loader.GLLoadTask;
import context.GLContext;
import context.visuals.lwjgl.VertexBufferObject;

public final class VertexBufferObjectLoadTask extends GLLoadTask<VertexBufferObject> {

	private GLContext context;
	private float[] indices;
	private int dataSize;

	public VertexBufferObjectLoadTask(GLContext context, float[] indices, int dataSize) {
		this(new CountDownLatch(1), context, indices, dataSize);
	}

	public VertexBufferObjectLoadTask(CountDownLatch countDownLatch, GLContext context, float[] indices, int dataSize) {
		super(countDownLatch);
		this.context = context;
		this.indices = indices;
		this.dataSize = dataSize;
	}

	@Override
	protected void loadIO() throws IOException {
	}

	@Override
	protected VertexBufferObject loadGL() {
		VertexBufferObject vbo = new VertexBufferObject(context, indices, dataSize);
		vbo.generateId();
		vbo.loadData();
		return vbo;
	}

}
