package context.visuals.builtin;

import context.GLContext;
import context.visuals.lwjgl.ElementBufferObject;
import context.visuals.lwjgl.VertexArrayObject;
import context.visuals.lwjgl.VertexBufferObject;

public class RectangleVertexArrayObject {

	private static final float[] POSITIONS = {
			1.0f, 1.0f, 0.0f,
			1.0f, 0.0f, 0.0f,
			0.0f, 0.0f, 0.0f,
			0.0f, 1.0f, 0.0f
	};

	private static final float[] TEXTURE_COORDINATES = {
			1.0f, 1.0f,
			1.0f, 0.0f,
			0.0f, 0.0f,
			0.0f, 1.0f
	};

	private static final int[] INDICES = {
			0, 1, 2,
			0, 2, 3
	};

	/**
	 * The VAO must be created before the getter is called
	 */
	public static VertexArrayObject createRectangleVAO(GLContext glContext) {
		ElementBufferObject ebo = new ElementBufferObject(INDICES);
		VertexBufferObject positionsVBO = new VertexBufferObject(POSITIONS, 3);
		VertexBufferObject textureCoordinatesVBO = new VertexBufferObject(TEXTURE_COORDINATES, 2);
		VertexArrayObject vao = new VertexArrayObject();
		vao.generateId();
		ebo.generateId();
		ebo.loadData(glContext);
		vao.setEbo(ebo);
		positionsVBO.generateId();
		positionsVBO.loadData(glContext);
		vao.attachVBO(positionsVBO);
		textureCoordinatesVBO.generateId();
		textureCoordinatesVBO.loadData(glContext);
		vao.attachVBO(textureCoordinatesVBO);
		vao.enableVertexAttribPointers(glContext);
		return vao;
	}

}
