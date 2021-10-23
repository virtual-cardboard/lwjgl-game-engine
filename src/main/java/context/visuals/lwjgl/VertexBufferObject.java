package context.visuals.lwjgl;

import static org.lwjgl.opengl.GL15.*;

import context.GLContext;

/**
 * An object that contains data about the available vertices able to be used in
 * a {@link VertexArrayObject}. Use {@link ElementBufferObject} to determine
 * which vertices to use.
 * 
 * @author Jay
 *
 */
public class VertexBufferObject extends GLRegularObject {

	private int id;
	private float[] data;
	private int numColumns;

	/**
	 * Creates a {@link VertexBufferObject} with the data. The data is treated as
	 * having <code>x</code> rows and <code>vertexDataSize</code> columns, where
	 * <code>x = data.length / vertexDataSize</code>.
	 * 
	 * @param data       float array of values
	 * @param numColumns the number of columns in the data
	 */
	public VertexBufferObject(final float[] data, int numColumns) {
		this.data = data;
		this.numColumns = numColumns;
	}

	public void loadData(GLContext glContext) {
		bind(glContext);
		glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
		glContext.bufferID = -1;
	}

	public void delete() {
		glDeleteBuffers(id);
	}

	@Override
	void bind(GLContext glContext) {
		if (glContext.bufferID == id) return;
		glBindBuffer(GL_ARRAY_BUFFER, id);
		glContext.bufferID = id;
	}

	public void generateId() {
		this.id = glGenBuffers();
	}

	public int getNumColumns() {
		return numColumns;
	}

}
