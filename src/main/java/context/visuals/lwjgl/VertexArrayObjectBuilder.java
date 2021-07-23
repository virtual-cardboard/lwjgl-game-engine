package context.visuals.lwjgl;

import java.util.Queue;

import common.loader.linktask.CreateVertexArrayObjectLinkTask;
import common.loader.linktask.LinkTask;

public class VertexArrayObjectBuilder {

	private final Queue<LinkTask> linkTasks;
	private volatile VertexArrayObject vao;

	public VertexArrayObjectBuilder(Queue<LinkTask> linkTasks) {
		this.linkTasks = linkTasks;
	}

	public CreateVertexArrayObjectLinkTask createRectangleVertexArrayObject() {
		return createVertexArrayObject(new VertexArrayObject(), new ElementBufferObject(RectangleMeshData.INDICES),
				new VertexBufferObject(RectangleMeshData.POSITIONS, 3), new VertexBufferObject(RectangleMeshData.TEXTURE_COORDINATES, 2));
	}

	public CreateVertexArrayObjectLinkTask createVertexArrayObject(VertexArrayObject vao, ElementBufferObject ebo, VertexBufferObject... vbos) {
		this.vao = vao;
		CreateVertexArrayObjectLinkTask createVaoLinkTask = new CreateVertexArrayObjectLinkTask(vao, ebo, vbos);
		linkTasks.add(createVaoLinkTask);
		return createVaoLinkTask;
	}

	public VertexArrayObject getVertexArrayObject() {
		VertexArrayObject copy = vao;
		vao = null;
		return copy != null && copy.isLinked() ? copy : null;
	}

	public boolean isBuilt() {
		return vao != null && vao.isLinked();
	}

}
