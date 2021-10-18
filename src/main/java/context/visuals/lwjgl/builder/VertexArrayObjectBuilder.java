package context.visuals.lwjgl.builder;

import java.util.concurrent.Future;

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
	protected Future<VertexArrayObject> build(GLObjectFactory glFactory) {
		return glFactory.build(this);
	}

	ElementBufferObject getEbo() {
		return ebo;
	}

	VertexBufferObject[] getVbos() {
		return vbos;
	}

}
