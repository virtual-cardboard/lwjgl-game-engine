package engine.common.math;

public class ProjectionTransformation extends Transformation {

	private int windowWidth;
	private int windowHeight;
	private final float fov = 90;
	private final float nearPlane = 0.001f;
	private final float farPlane = 40;

	public ProjectionTransformation(int windowWidth, int windowHeight) {
		loadWindowDimensions(windowWidth, windowHeight);
	}

	public void loadWindowDimensions(int windowWidth, int windowHeight) {
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
		raiseFlag();
	}

	@Override
	protected void calculateMatrix() {
		float aspectRatio = (float) windowWidth / (float) windowHeight;
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(fov / 2f))) * aspectRatio);
		float x_scale = y_scale / aspectRatio;
		float frustum_length = farPlane - nearPlane;

		matrix = new Matrix4f();
		matrix.m00 = x_scale;
		matrix.m11 = y_scale;
		matrix.m22 = -((farPlane + nearPlane) / frustum_length);
		matrix.m23 = -1;
		matrix.m32 = -((2 * nearPlane * farPlane) / frustum_length);
		matrix.m33 = 0;
	}

	public int getWindowWidth() {
		return windowWidth;
	}

	public int getWindowHeight() {
		return windowHeight;
	}

}
