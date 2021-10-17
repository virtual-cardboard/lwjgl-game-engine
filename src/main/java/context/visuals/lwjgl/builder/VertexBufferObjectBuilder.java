package context.visuals.lwjgl.builder;

import context.GLContext;
import context.visuals.lwjgl.VertexBufferObject;

public final class VertexBufferObjectBuilder extends RegularGLObjectBuilder<VertexBufferObject> {

	private float[] data;
	private int vertexDataSize;

	public VertexBufferObjectBuilder(GLContext context, float[] data, int vertexDataSize) {
		super(context);
		this.data = data;
		this.vertexDataSize = vertexDataSize;
	}

	@Override
	public VertexBufferObject build() {
		VertexBufferObject vbo = new VertexBufferObject(context, data, vertexDataSize);
		vbo.generateId();
		vbo.loadData();
		return vbo;
	}

}
