package context.visuals.gui.constraint.dimension;

public class PixelDimensionConstraint extends GuiDimensionConstraint {

	private int pixels;

	public PixelDimensionConstraint(int pixels) {
		this.pixels = pixels;
	}

	public int getPixels() {
		return pixels;
	}

	public void setPixels(int pixels) {
		this.pixels = pixels;
	}

	@Override
	public float calculateValue(float start, float end) {
		return pixels;
	}

}
