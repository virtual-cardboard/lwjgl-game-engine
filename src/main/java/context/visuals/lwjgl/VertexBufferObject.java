package context.visuals.lwjgl;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;

public class VertexBufferObject {

	private static VertexBufferObject currentlyBound;

	private int id;

	private float[] data;

	public VertexBufferObject(final float[] data) {
		id = glGenBuffers();
		this.data = data;
	}

	public void loadData() {
		bind();
		glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
	}

	private void bind() {
		if (currentlyBound == this) {
			return;
		}
		glBindBuffer(GL_ARRAY_BUFFER, id);
		currentlyBound = this;
	}

	public int getId() {
		return id;
	}

}
