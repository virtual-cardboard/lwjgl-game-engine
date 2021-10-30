package context.visuals.lwjgl;

import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;

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

	public ElementBufferObject(final int[] indices) {
		this.data = indices;
	}

	public void loadData(GLContext glContext) {
		bind(glContext);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, data, GL_STATIC_DRAW);
		glContext.bufferID = -1;
	}

	public void delete() {
		glDeleteBuffers(id);
	}

	protected void bind(GLContext glContext) {
		verifyInitialized();
		if (glContext.bufferID == id) {
			return;
		}
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, id);
		glContext.bufferID = id;
	}

	public void genId() {
		this.id = glGenBuffers();
		confirmInitialization();
	}

	public int size() {
		return data.length;
	}

}
