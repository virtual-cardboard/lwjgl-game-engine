package context.visuals.lwjgl;

import java.util.Queue;

import common.loader.linktask.CreateVaoLinkTask;
import common.loader.linktask.LinkTask;

public class VaoBuilder {

	private Queue<LinkTask> linkTasks;

	public VaoBuilder(Queue<LinkTask> linkTasks) {
		this.linkTasks = linkTasks;
	}

	public CreateVaoLinkTask createVao(VertexArrayObject vao) {
		CreateVaoLinkTask createVaoLinkTask = new CreateVaoLinkTask(vao);
		linkTasks.add(createVaoLinkTask);
		return createVaoLinkTask;
	}

}
