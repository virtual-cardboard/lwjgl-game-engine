package context.visuals.lwjgl.builder;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import context.GLContext;
import context.visuals.lwjgl.ElementBufferObject;
import context.visuals.lwjgl.GLObject;
import context.visuals.lwjgl.VertexArrayObject;
import context.visuals.lwjgl.VertexBufferObject;

public final class GLObjectFactory {

	private GLContext context;

	public GLObjectFactory(GLContext context) {
		this.context = context;
	}

	public <T extends GLObject> Future<T> createUsingBuilder(GLObjectBuilder<T> builder) {
		return builder.build(this);
	}

	Future<VertexBufferObject> build(VertexBufferObjectBuilder vboBuilder) {
		VertexBufferObject vbo = new VertexBufferObject(context, vboBuilder.getData(), vboBuilder.getNumColumns());
		vbo.generateId();
		vbo.loadData();
		return CompletableFuture.completedFuture(vbo);
	}

	Future<ElementBufferObject> build(ElementBufferObjectBuilder eboBuilder) {
		ElementBufferObject ebo = new ElementBufferObject(context, eboBuilder.getIndices());
		ebo.generateId();
		ebo.loadData();
		return CompletableFuture.completedFuture(ebo);
	}

	Future<VertexArrayObject> build(VertexArrayObjectBuilder vaoBuilder) {
		VertexArrayObject vao = new VertexArrayObject(context);
		vao.setEbo(vaoBuilder.getEbo());
		VertexBufferObject[] vbos = vaoBuilder.getVbos();
		for (int i = 0; i < vbos.length; i++)
			vao.attachVBO(vbos[i]);
		vao.enableVertexAttribPointers();
		return CompletableFuture.completedFuture(vao);
	}

}
