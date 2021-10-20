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
public class ElementBufferObject extends RegularGLObject {

	private int id;
	private int[] data;

	public ElementBufferObject(GLContext context, final int[] indices) {
		super(context);
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
		if (context.eboID == id) return;
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, id);
		context.eboID = id;
	}

	public void generateId() {
		this.id = glGenBuffers();
	}

	public int size() {
		return data.length;
	}

}
