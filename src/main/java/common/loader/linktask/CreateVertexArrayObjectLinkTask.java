package common.loader.linktask;

import java.util.concurrent.CountDownLatch;

import context.visuals.lwjgl.ElementBufferObject;
import context.visuals.lwjgl.VertexArrayObject;
import context.visuals.lwjgl.VertexBufferObject;

public class CreateVertexArrayObjectLinkTask extends LinkTask {

	private VertexArrayObject vao;
	private ElementBufferObject ebo;
	private VertexBufferObject[] vbos;

	public CreateVertexArrayObjectLinkTask(VertexArrayObject vao, ElementBufferObject ebo, VertexBufferObject[] vbos) {
		this(new CountDownLatch(1), vao, ebo, vbos);
	}

	public CreateVertexArrayObjectLinkTask(CountDownLatch countDownLatch, VertexArrayObject vao, ElementBufferObject ebo, VertexBufferObject[] vbos) {
		super(countDownLatch);
		this.vao = vao;
		this.ebo = ebo;
		this.vbos = vbos;
	}

	@Override
	public void doRun() {
		vao.generateId();
		ebo.generateId();
		vao.setEbo(ebo);
		for (int i = 0; i < vbos.length; i++) {
			VertexBufferObject vbo = vbos[i];
			vbo.generateId();
			vao.attachVBO(vbo);
		}
		vao.enableVertexAttribPointers();
	}

	public VertexArrayObject getVao() {
		return vao;
	}

	public ElementBufferObject getEbo() {
		return ebo;
	}

	public VertexBufferObject[] getVbos() {
		return vbos;
	}

}
