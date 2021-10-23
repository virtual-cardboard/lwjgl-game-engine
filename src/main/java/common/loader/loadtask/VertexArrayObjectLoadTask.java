package common.loader.loadtask;

import java.util.concurrent.Future;

import common.loader.GLContainerObjectLoadTask;
import common.loader.GameLoader;
import context.GLContext;
import context.visuals.lwjgl.ElementBufferObject;
import context.visuals.lwjgl.VertexArrayObject;
import context.visuals.lwjgl.VertexBufferObject;

/**
 * A load task for a {@link VertexArrayObject}. Submitting this load task to the
 * {@link GameLoader} immediately returns a completed {@link Future} to
 * {@link Future#get() get()} from. Only add this load task from the rendering
 * thread.
 * 
 * @author Jay
 *
 */
public final class VertexArrayObjectLoadTask extends GLContainerObjectLoadTask<VertexArrayObject> {

	private ElementBufferObject ebo;
	private VertexBufferObject[] vbos;

	public VertexArrayObjectLoadTask(ElementBufferObject ebo, VertexBufferObject... vbos) {
		this.ebo = ebo;
		this.vbos = vbos;
	}

	@Override
	protected VertexArrayObject loadGL(GLContext glContext) {
		VertexArrayObject vao = new VertexArrayObject();
		vao.generateId();
		vao.setEbo(ebo);
		for (int i = 0; i < vbos.length; i++)
			vao.attachVBO(vbos[i]);
		vao.enableVertexAttribPointers(glContext);
		return vao;
	}

}
