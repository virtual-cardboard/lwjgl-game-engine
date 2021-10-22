package context.visuals.lwjgl;

import static org.lwjgl.opengl.GL15.*;

import context.GLContext;

/**
 * An object that tells OpenGL which vertices in a {@link VertexBufferObject} to
 * display.
 * 
 * @see VertexArrayObject
 * 
 * @author Jay
 *
 */
public class ElementBufferObject extends GLRegularObject {

	private int id;
	private int[] data;

	public ElementBufferObject(GLContext context, final int[] indices) {
		super(context);
		this.data = indices;
	}

	public void loadData() {
		bind();
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, data, GL_STATIC_DRAW);
		context.bufferID = -1;
	}

	public void delete() {
		glDeleteBuffers(id);
	}

	void bind() {
		if (context.bufferID == id) return;
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, id);
		context.bufferID = id;
	}

	public void generateId() {
		this.id = glGenBuffers();
	}

	public int size() {
		return data.length;
	}

}
