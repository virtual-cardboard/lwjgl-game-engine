package common.loader.loadtask;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import common.loader.GLLoadTask;
import context.GLContext;
import context.visuals.lwjgl.ElementBufferObject;
import context.visuals.lwjgl.VertexArrayObject;
import context.visuals.lwjgl.VertexBufferObject;

public final class VertexArrayObjectLoadTask extends GLLoadTask {

	private GLContext context;
	private ElementBufferObject ebo;
	private VertexBufferObject[] vbos;

	private VertexArrayObject vao;

	public VertexArrayObjectLoadTask(GLContext context, ElementBufferObject ebo, VertexBufferObject... vbos) {
		this(new CountDownLatch(1), context, ebo, vbos);
	}

	public VertexArrayObjectLoadTask(CountDownLatch countDownLatch, GLContext context, ElementBufferObject ebo, VertexBufferObject... vbos) {
		super(countDownLatch);
		this.context = context;
		this.ebo = ebo;
		this.vbos = vbos;
	}

	@Override
	protected void loadIO() throws IOException {
		vao = new VertexArrayObject(context);
	}

	@Override
	protected void loadGL() {
		ebo.generateId();
		ebo.loadData();
		vao.setEbo(ebo);
		for (int i = 0; i < vbos.length; i++) {
			vbos[i].generateId();
			vbos[i].loadData();
			vao.attachVBO(vbos[i]);
		}
	}

	public VertexArrayObject getVertexArrayObject() {
		return vao;
	}

}
