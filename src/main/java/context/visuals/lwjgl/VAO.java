package context.visuals.lwjgl;

import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

/**
 * An attribute object stores all the information for each vertex of a mesh. It
 * stores them in the form of buffer objects
 * 
 * @author Jay
 *
 */
public class VAO {

	private static List<Integer> vaos = new ArrayList<>();

	private int vaoId;
	private int vertexCount;

	/**
	 * Must bind and unbind VAO before attaching VBO/EBOs. The VAO is already
	 * default bound upon creation so do not create any other. VAOs until finished
	 * attaching VBO and EBOs for the most recently created vao.
	 * 
	 * @param data
	 */
	protected void attachEBO(int[] data) {
		EBO ebo = new EBO();
		ebo.bindEBO();
		ebo.loadData(data);
	}

	private void attachVBO(int attributeIndex, int dimensions, float[] data) {
		VBO vbo = new VBO();
		vbo.bindVBO();
		vbo.loadData(data);
		glVertexAttribPointer(attributeIndex, dimensions, GL11.GL_FLOAT, false, 0, 0);
		vbo.unbindVBO();
	}

	/**
	 * Binding the attribute object so opengl knows to write to it.
	 */
	public void bindVAO() {
		glBindVertexArray(vaoId);
	}

	/**
	 * Remeber to unbind after finished attaching VBOS.
	 */
	public void unbindVAO() {
		glBindVertexArray(0);
	}

	public int getVaoId() {
		return vaoId;
	}

	public int getVertexCount() {
		return vertexCount;
	}

	/**
	 * Method for deleting all the attribute objects when program is closing Called
	 * in Visual class's cleanUp method.
	 */
	public static void cleanUp() {
		for (int vao : vaos) {
			glDeleteVertexArrays(vao);
		}
	}

	public void interpret(RectangleMeshData data) {
		vaoId = glGenVertexArrays();
		vaos.add(vaoId);
		bindVAO();
		attachVBO(0, 2, data.getQuadVertices());
		vertexCount = 6;
		unbindVAO();
	}

}
