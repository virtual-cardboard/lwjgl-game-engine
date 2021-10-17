package context.visuals.lwjgl.builder;

import context.GLContext;
import context.visuals.lwjgl.ElementBufferObject;
import context.visuals.lwjgl.VertexArrayObject;
import context.visuals.lwjgl.VertexBufferObject;

public class VertexArrayObjectBuilder extends ContainerGLObjectBuilder<VertexArrayObject> {

	private ElementBufferObject ebo;
	private VertexBufferObject[] vbos;

	public VertexArrayObjectBuilder(GLContext context, ElementBufferObject ebo, VertexBufferObject... vbos) {
		super(context);
		this.ebo = ebo;
		this.vbos = vbos;
	}

	@Override
	public VertexArrayObject build() {
		VertexArrayObject vao = new VertexArrayObject(context);
		vao.generateId();
		vao.setEbo(ebo);
		for (int i = 0, l = vbos.length; i < l; i++) {
			VertexBufferObject vbo = vbos[i];
			vao.attachVBO(vbo);
		}
		vao.enableVertexAttribPointers();
		return vao;
	}

}
