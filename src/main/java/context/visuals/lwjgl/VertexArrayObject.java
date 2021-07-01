package context.visuals.lwjgl;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.util.ArrayList;
import java.util.List;

public class VertexArrayObject {

	private static VertexArrayObject currentlyBound;

	private int id;

	private int nextAttribIndex = 0;

	private List<VertexBufferObject> vbos;
	private ElementBufferObject ebo;

	public VertexArrayObject() {
		id = glGenVertexArrays();
		vbos = new ArrayList<>();
	}

	public void display() {
		bind();
		if (ebo == null) {
			glDrawArrays(GL_TRIANGLES, 0, 3);
		} else {
			ebo.bind();
			glDrawElements(GL_TRIANGLES, ebo.size(), GL_UNSIGNED_INT, 0);
		}
	}

	public void delete() {
		glDeleteVertexArrays(id);
	}

	public void setVertexAttributePointer(int size, int stride, int offset) {
		bind();
		glVertexAttribPointer(nextAttribIndex, size, GL_FLOAT, false, stride * Float.BYTES, offset * Float.BYTES);
		glEnableVertexAttribArray(nextAttribIndex++);
	}

	private void bind() {
		if (currentlyBound == this) {
			return;
		}
		glBindVertexArray(id);
		currentlyBound = this;
	}

	public void attachVBO(VertexBufferObject vbo) {
		bind();
		vbos.add(vbo);
	}

	public int getId() {
		return id;
	}

	public void setEbo(ElementBufferObject ebo) {
		bind();
		this.ebo = ebo;
	}

}
