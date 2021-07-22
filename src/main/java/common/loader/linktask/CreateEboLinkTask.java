package common.loader.linktask;

import context.visuals.lwjgl.ElementBufferObject;

public class CreateEboLinkTask extends LinkTask {

	private ElementBufferObject ebo;

	public CreateEboLinkTask(ElementBufferObject ebo) {
		this.ebo = ebo;
	}

	@Override
	public void doRun() {
		ebo.generateId();
		ebo.loadData();
	}

}
