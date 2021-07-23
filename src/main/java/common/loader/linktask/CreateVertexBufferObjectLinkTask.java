package common.loader.linktask;

import context.visuals.lwjgl.VertexBufferObject;

public class CreateVertexBufferObjectLinkTask extends LinkTask {

	private VertexBufferObject vbo;

	public CreateVertexBufferObjectLinkTask(VertexBufferObject vbo) {
		this.vbo = vbo;
	}

	@Override
	public void doRun() {
		vbo.generateId();
		vbo.loadData();
	}

}
