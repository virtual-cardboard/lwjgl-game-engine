package context.visuals.gui.constraint.dimension;

public class PixelDimensionConstraint extends GuiDimensionConstraint {

	private float pixels;

	/**
	 * Creates a new PixelDimensionConstraint with the given number of pixels.
	 * <p>
	 * If pixels is positive, then the dimension will be set to <code>pixels</code>.
	 * Otherwise, the dimension will be set to the parent dimension minus <code>abs(pixels)</code>.
	 *
	 * @param pixels the number of pixels
	 */
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
		// Supports negative pixels
		return pixels > 0 ? pixels : end + pixels;
	}

}
