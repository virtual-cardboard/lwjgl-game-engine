package common.loader.linktask;

import context.visuals.lwjgl.VertexBufferObject;

public class CreateVboLinkTask extends LinkTask {

	private VertexBufferObject vbo;

	public CreateVboLinkTask(VertexBufferObject vbo) {
		this.vbo = vbo;
	}

	@Override
	public void doRun() {
		vbo.generateId();
		vbo.loadData();
	}

}
