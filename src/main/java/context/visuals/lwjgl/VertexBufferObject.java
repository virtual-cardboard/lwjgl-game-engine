package context.visuals.lwjgl;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

import context.GLContext;

/**
 * An object that contains data about the available vertices able to be used in
 * a {@link VertexArrayObject}. Use {@link ElementBufferObject} to determine
 * which vertices to use.
 *
 * @author Jay
 */
public class VertexBufferObject extends GLRegularObject {

	private int id;
	private final float[] data;
	private final int numColumns;

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
		glContext.bufferID = 0;
	}

	protected void enableVertexAttribPointer(GLContext glContext, int i) {
		bind(glContext);
		glVertexAttribPointer(i, numColumns, GL_FLOAT, false, numColumns * Float.BYTES, 0);
		glEnableVertexAttribArray(i);
	}

	public void delete() {
		glDeleteBuffers(id);
	}

	private void bind(GLContext glContext) {
		verifyInitialized();
		if (glContext.bufferID == id) {
			return;
		}
		glBindBuffer(GL_ARRAY_BUFFER, id);
		glContext.bufferID = id;
	}

	public void genID() {
		this.id = glGenBuffers();
		initialize();
	}

	public int getNumColumns() {
		return numColumns;
	}

}
