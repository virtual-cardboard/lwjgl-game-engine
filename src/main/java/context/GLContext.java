package context;

import engine.common.math.Vector2i;

public class GLContext {

	public int vaoID = 0;
	public int bufferID = 0;
	public int framebufferID = 0;
	public int renderbufferID = 0;
	public int shaderProgramID = 0;
	public int[] textureIDs = new int[48];

	private Vector2i windowDim;

	public GLContext() {
	}

	public float width() {
		return windowDim.x;
	}

	public float height() {
		return windowDim.y;
	}

	public Vector2i windowDim() {
		return windowDim;
	}

	public void setWindowDim(Vector2i windowDim) {
		this.windowDim = windowDim;
	}

}
