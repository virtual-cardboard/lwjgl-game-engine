package context.visuals.gui.constraint.position;

import java.util.Objects;

import context.visuals.gui.constraint.dimension.GuiDimensionConstraint;

public class PixelPositionConstraint extends GuiPositionConstraint {

	private float pixels;
	private GuiDimensionConstraint dimensionConstraint;

	public PixelPositionConstraint(float pixels) {
		this.pixels = pixels;
	}

	public PixelPositionConstraint(float pixels, GuiDimensionConstraint dimensionConstraint) {
		this.pixels = pixels;
		this.dimensionConstraint = Objects.requireNonNull(dimensionConstraint);
	}

	@Override
	public float get(float start, float end) {
		return dimensionConstraint == null ? start + pixels : end - pixels - dimensionConstraint.get(start, end);
	}

	public float getPixels() {
		return pixels;
	}

	public void setPixels(float pixels) {
		this.pixels = pixels;
	}

	public boolean leading() {
		return dimensionConstraint == null;
	}

	public boolean trailing() {
		return !leading();
	}

}
