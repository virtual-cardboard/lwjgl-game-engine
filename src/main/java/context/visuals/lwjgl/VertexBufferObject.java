package context.visuals.lwjgl;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;

/**
 * An object that contains data about the available vertices able to be used in
 * a {@link VertexArrayObject}. Use {@link ElementBufferObject} to determine
 * which vertices to use.
 * 
 * @author Jay
 *
 */
public class VertexBufferObject {

	private static VertexBufferObject currentlyBound;

	private int id;
	private float[] data;
	private boolean linked;

	public VertexBufferObject(final float[] data) {
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

	public void generateId() {
		if (linked) {
			throw new IllegalStateException("Tried to generate VBO ID when already generated.");
		}
		this.id = glGenBuffers();
		linked = true;
	}

	public boolean isLinked() {
		return linked;
	}

}
