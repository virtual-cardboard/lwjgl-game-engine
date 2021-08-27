package context.visuals.lwjgl;

import static context.visuals.lwjgl.RectangleMeshData.INDICES;
import static context.visuals.lwjgl.RectangleMeshData.POSITIONS;
import static context.visuals.lwjgl.RectangleMeshData.TEXTURE_COORDINATES;

import java.util.Queue;

import common.loader.AbstractBuilder;
import common.loader.linktask.CreateVertexArrayObjectLinkTask;
import common.loader.linktask.LinkTask;

public class VertexArrayObjectBuilder extends AbstractBuilder<VertexArrayObject> {

	private final Queue<LinkTask> linkTasks;

	public VertexArrayObjectBuilder(Queue<LinkTask> linkTasks) {
		this.linkTasks = linkTasks;
	}

	public void createRectangleVertexArrayObject() {
		createVertexArrayObject(new ElementBufferObject(INDICES), new VertexBufferObject(POSITIONS, 3), new VertexBufferObject(TEXTURE_COORDINATES, 2));
	}

	public void createVertexArrayObject(ElementBufferObject ebo, VertexBufferObject... vbos) {
		VertexArrayObject vao = new VertexArrayObject();
		CreateVertexArrayObjectLinkTask createVaoLinkTask = new CreateVertexArrayObjectLinkTask(vao, ebo, vbos);
		linkTasks.add(createVaoLinkTask);
		set(vao);
	}

	@Override
	public boolean isBuilt() {
		VertexArrayObject vao = get();
		return vao != null && vao.isLinked();
	}

}
