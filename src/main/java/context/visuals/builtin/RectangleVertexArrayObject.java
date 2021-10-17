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
	public static VertexArrayObject createRectangleVAO(GLContext context) {
		ElementBufferObject ebo = new ElementBufferObject(context, INDICES);
		VertexBufferObject positionsVBO = new VertexBufferObject(context, POSITIONS, 3);
		VertexBufferObject textureCoordinatesVBO = new VertexBufferObject(context, TEXTURE_COORDINATES, 2);
		VertexArrayObject vao = new VertexArrayObject(context);
		vao.generateId();
		ebo.generateId();
		ebo.loadData();
		vao.setEbo(ebo);
		positionsVBO.generateId();
		positionsVBO.loadData();
		vao.attachVBO(positionsVBO);
		textureCoordinatesVBO.generateId();
		textureCoordinatesVBO.loadData();
		vao.attachVBO(textureCoordinatesVBO);
		vao.enableVertexAttribPointers();
		return vao;
	}

}
