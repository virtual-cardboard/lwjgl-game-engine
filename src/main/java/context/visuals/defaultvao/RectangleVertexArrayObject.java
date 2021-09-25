package context.visuals.defaultvao;

import context.visuals.lwjgl.ElementBufferObject;
import context.visuals.lwjgl.VertexArrayObject;
import context.visuals.lwjgl.VertexBufferObject;

public class RectangleVertexArrayObject {

	public static final VertexArrayObject rectangleVAO = new VertexArrayObject();

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

	public static void createRectangleVAO() {
		rectangleVAO.generateId();
		rectangleVAO.bind();
		ElementBufferObject ebo = new ElementBufferObject(INDICES);
		VertexBufferObject positionsVBO = new VertexBufferObject(POSITIONS, 3);
		VertexBufferObject textureCoordinatesVBO = new VertexBufferObject(TEXTURE_COORDINATES, 2);
		ebo.generateId();
		ebo.loadData();
		rectangleVAO.setEbo(ebo);
		positionsVBO.generateId();
		positionsVBO.loadData();
		rectangleVAO.attachVBO(positionsVBO);
		textureCoordinatesVBO.generateId();
		textureCoordinatesVBO.loadData();
		rectangleVAO.attachVBO(textureCoordinatesVBO);
		rectangleVAO.enableVertexAttribPointers();
	}

}
