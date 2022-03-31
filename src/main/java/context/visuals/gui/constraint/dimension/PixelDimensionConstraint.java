package context.visuals.gui.constraint.dimension;

public class PixelDimensionConstraint extends GuiDimensionConstraint {

	private float pixels;

	public PixelDimensionConstraint(float pixels) {
		this.pixels = pixels;
	}

	public float getPixels() {
		return pixels;
	}

	public void setPixels(float pixels) {
		this.pixels = pixels;
	}

	@Override
	public float doGet(float start, float end) {
		return pixels;
	}

}
