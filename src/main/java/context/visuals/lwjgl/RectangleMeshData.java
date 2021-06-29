package context.visuals.lwjgl;

public class RectangleMeshData {

	// The quad is going to be the same for every GUI
	private static final float[] QUAD_VERTICES = { 0, 0, 0, 1, 1, 0, 1, 1 };

	public float[] getQuadVertices() {
		return QUAD_VERTICES;
	}

}
