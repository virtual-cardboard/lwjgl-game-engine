package context.visuals.defaultvao;

import context.visuals.lwjgl.ElementBufferObject;
import context.visuals.lwjgl.VertexArrayObject;
import context.visuals.lwjgl.VertexArrayObjectBuilder;
import context.visuals.lwjgl.VertexBufferObject;

public class RectangleVertexArrayObject {

	private static VertexArrayObject rectangleVAO = null;

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
	public static void createRectangleVAO() {
		if (rectangleVAO != null) {
			return;
		}
		ElementBufferObject ebo = new ElementBufferObject(INDICES);
		VertexBufferObject positionsVBO = new VertexBufferObject(POSITIONS, 3);
		VertexBufferObject textureCoordinatesVBO = new VertexBufferObject(TEXTURE_COORDINATES, 2);
		rectangleVAO = new VertexArrayObjectBuilder(ebo, positionsVBO, textureCoordinatesVBO).build();
	}

	public static VertexArrayObject rectangleVAO() {
		return rectangleVAO;
	}

}
