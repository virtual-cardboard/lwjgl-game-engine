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
public final class VetexArrayObjectLoadTask extends GLContainerObjectLoadTask<VertexArrayObject> {

	private GLContext glContext;
	private ElementBufferObject ebo;
	private VertexBufferObject[] vbos;

	public VetexArrayObjectLoadTask(GLContext glContext, ElementBufferObject ebo, VertexBufferObject... vbos) {
		this.glContext = glContext;
		this.ebo = ebo;
		this.vbos = vbos;
	}

	@Override
	protected VertexArrayObject loadGL() {
		VertexArrayObject vao = new VertexArrayObject(glContext);
		vao.setEbo(ebo);
		for (int i = 0; i < vbos.length; i++)
			vao.attachVBO(vbos[i]);
		vao.enableVertexAttribPointers();
		return vao;
	}

}
