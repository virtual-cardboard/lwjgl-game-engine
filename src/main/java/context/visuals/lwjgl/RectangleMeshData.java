package context.visuals.lwjgl;

public class RectangleMeshData {

	public static final float[] VERTICES = {
			1.0f, 1.0f, 0.0f, /**/1.0f, 1.0f, // top right
			1.0f, 0.0f, 0.0f, /**/1.0f, 0.0f, // bottom right
			0.0f, 0.0f, 0.0f, /**/0.0f, 0.0f, // bottom left
			0.0f, 1.0f, 0.0f, /**/0.0f, 1.0f // top left
	};

	public static final int[] INDICES = {
			0, 1, 2,
			0, 2, 3
	};

	public static VertexArrayObject generateRectangle() {
		VertexArrayObject vao = new VertexArrayObject();
		VertexBufferObject vbo = new VertexBufferObject(VERTICES);
		ElementBufferObject ebo = new ElementBufferObject(INDICES);
		vao.attachVBO(vbo);
		vao.setEbo(ebo);
		vbo.loadData();
		ebo.loadData();
		vao.setVertexAttributePointer(3, 5, 0);
		vao.setVertexAttributePointer(2, 5, 3);
		return vao;
	}

}
