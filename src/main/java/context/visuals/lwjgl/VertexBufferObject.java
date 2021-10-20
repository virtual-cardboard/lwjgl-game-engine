package context.visuals.lwjgl;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;

import context.GLContext;

/**
 * An object that contains data about the available vertices able to be used in
 * a {@link VertexArrayObject}. Use {@link ElementBufferObject} to determine
 * which vertices to use.
 * 
 * @author Jay
 *
 */
public class VertexBufferObject extends RegularGLObject {

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
	public VertexBufferObject(GLContext context, final float[] data, int numColumns) {
		super(context);
		this.data = data;
		this.numColumns = numColumns;
	}

	public void loadData() {
		bind();
		glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
	}

	void bind() {
		if (context.vboID == id) return;
		glBindBuffer(GL_ARRAY_BUFFER, id);
		context.vboID = id;
	}

	public void generateId() {
		this.id = glGenBuffers();
	}

	public int getNumColumns() {
		return numColumns;
	}

	public static class VertexBufferObjectRawData {

		public float[] data;
		public int numColumns;

		public VertexBufferObjectRawData(float[] data, int numColumns) {
			this.data = data;
			this.numColumns = numColumns;
		}

		public VertexBufferObject createVertexBufferObject(GLContext context) {
			return new VertexBufferObject(context, data, numColumns);
		}

	}

}
