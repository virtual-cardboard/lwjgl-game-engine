package common.loader.linktask;

import java.util.concurrent.CountDownLatch;

import context.visuals.lwjgl.VertexArrayObject;

public class CreateVaoLinkTask extends LinkTask {

	private VertexArrayObject vao;

	public CreateVaoLinkTask(VertexArrayObject vao) {
		this(new CountDownLatch(1), vao);
	}

	public CreateVaoLinkTask(CountDownLatch countDownLatch, VertexArrayObject vao) {
		super(countDownLatch);
		this.vao = vao;
	}

	@Override
	public void doRun() {
		vao.generateId();
	}

}
