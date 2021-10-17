package context.visuals.lwjgl;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.util.ArrayList;
import java.util.List;

import context.GLContext;

/**
 * An object that contains data about a group of vertices in OpenGL. Learn more
 * about them here: <a href=
 * "https://stackoverflow.com/questions/11821336/what-are-vertex-array-objects">
 * https://stackoverflow.com/questions/11821336/what-are-vertex-array-objects</a>
 * 
 * @see VertexBufferObject
 * @see ElementBufferObject
 * 
 * @author Jay
 *
 */
public class VertexArrayObject extends ContainerGLObject {

	private int id;
	private List<VertexBufferObject> vbos = new ArrayList<>();
	private ElementBufferObject ebo;

	public VertexArrayObject(GLContext context) {
		super(context);
	}

	public void display() {
		bind();
		ebo.bind();
		glDrawElements(GL_TRIANGLES, ebo.size(), GL_UNSIGNED_INT, 0);
	}

	public void delete() {
		glDeleteVertexArrays(id);
	}

	/**
	 * Tells OpenGL to enable the attached VBOs. Should only be called once after
	 * attaching all necessary VBOs.
	 * 
	 * @see VertexBufferObject
	 */
	public void enableVertexAttribPointers() {
		bind();
		for (int i = 0; i < vbos.size(); i++) {
			VertexBufferObject vbo = vbos.get(i);
			vbo.bind();
			int vertexDataSize = vbo.getVertexDataSize();
			glVertexAttribPointer(i, vertexDataSize, GL_FLOAT, false, vertexDataSize * Float.BYTES, 0);
			glEnableVertexAttribArray(i);
		}
	}

	private void bind() {
		if (context.vaoID == id) return;
		glBindVertexArray(id);
		context.vaoID = id;
	}

	public void attachVBO(VertexBufferObject vbo) {
		bind();
		vbos.add(vbo);
	}

	public void generateId() {
		this.id = glGenVertexArrays();
	}

	public void setEbo(ElementBufferObject ebo) {
		this.ebo = ebo;
	}

}
