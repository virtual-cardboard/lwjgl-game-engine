package context.visuals.lwjgl;

import static org.lwjgl.opengl.GL15.*;

public class ElementBufferObject {

	private static ElementBufferObject currentlyBound;

	private int id;

	private int[] data;

	public ElementBufferObject(final int[] indices) {
		id = glGenBuffers();
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

	public int size() {
		return data.length;
	}

}
