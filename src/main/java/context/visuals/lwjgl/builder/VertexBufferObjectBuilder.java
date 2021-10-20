package context.visuals.lwjgl.builder;

import java.util.concurrent.Future;

import context.GLContext;
import context.visuals.lwjgl.VertexBufferObject;
import context.visuals.lwjgl.VertexBufferObject.VertexBufferObjectRawData;

public final class VertexBufferObjectBuilder extends RegularGLObjectBuilder<VertexBufferObject> {

	private float[] data;
	private int numColumns;

	public VertexBufferObjectBuilder(GLContext glContext, VertexBufferObjectRawData vboRawData) {
		super(glContext);
		this.data = vboRawData.data;
		this.numColumns = vboRawData.numColumns;
	}

	@Override
	protected Future<VertexBufferObject> build(GLObjectFactory glFactory) {
		return glFactory.build(this);
	}

	float[] getData() {
		return data;
	}

	int getNumColumns() {
		return numColumns;
	}

}
