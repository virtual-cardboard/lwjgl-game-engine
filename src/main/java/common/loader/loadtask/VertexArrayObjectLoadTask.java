package common.loader.loadtask;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import common.loader.GLLoadTask;
import context.GLContext;
import context.visuals.lwjgl.ElementBufferObject;
import context.visuals.lwjgl.VertexArrayObject;
import context.visuals.lwjgl.VertexBufferObject;

public final class VertexArrayObjectLoadTask extends GLLoadTask<VertexArrayObject> {

	private GLContext context;
	private ElementBufferObject ebo;
	private List<VertexBufferObject> vbos;

	public VertexArrayObjectLoadTask(GLContext context, ElementBufferObject ebo, List<VertexBufferObject> vbos) {
		this(new CountDownLatch(1), context, ebo, vbos);
	}

	public VertexArrayObjectLoadTask(CountDownLatch countDownLatch, GLContext context, ElementBufferObject ebo, List<VertexBufferObject> vbos) {
		super(countDownLatch);
		this.context = context;
		this.ebo = ebo;
		this.vbos = vbos;
	}

	@Override
	protected void loadIO() throws IOException {
	}

	@Override
	protected VertexArrayObject loadGL() {
		VertexArrayObject vao = new VertexArrayObject(context);
		vao.setEbo(ebo);
		vbos.forEach(vao::attachVBO);
		return vao;
	}

}
