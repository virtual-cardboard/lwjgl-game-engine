package context.visuals.lwjgl;

import static org.lwjgl.opengl.GL15.*;

/**
 * An object that tells OpenGL which vertices in a {@link VertexBufferObject} to
 * display.
 * 
 * @see VertexArrayObject
 * 
 * @author Jay
 *
 */
public class ElementBufferObject {

	private static ElementBufferObject currentlyBound;

	private int id;
	private int[] data;
	private boolean linked;

	public ElementBufferObject(final int[] indices) {
		this.data = indices;
	}

	public void loadData() {
		bind();
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, data, GL_STATIC_DRAW);
	}

	public void delete() {
		glDeleteBuffers(id);
	}

	void bind() {
		if (currentlyBound == this) {
			return;
		}
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, id);
		currentlyBound = this;
	}

	public void generateId() {
		if (linked) {
			throw new IllegalStateException("Tried to generate EBO ID when already generated.");
		}
		this.id = glGenBuffers();
		linked = true;
	}

	public int size() {
		return data.length;
	}

	public boolean isLinked() {
		return linked;
	}

}
