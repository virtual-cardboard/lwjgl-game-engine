package context.visuals.gui.constraint.position;

import java.util.Objects;
import java.util.function.Supplier;

import context.visuals.gui.constraint.dimension.GuiDimensionConstraint;

public class PixelPositionConstraint extends GuiPositionConstraint {

	private Supplier<Float> pixelsSupplier;
	private float pixels;
	private GuiDimensionConstraint dimensionConstraint;

	public PixelPositionConstraint(float pixels) {
		this.pixels = pixels;
	}

	public PixelPositionConstraint(Supplier<Float> pixelsSupplier) {
		this.pixelsSupplier = pixelsSupplier;
	}

	public PixelPositionConstraint(Supplier<Float> pixelsSupplier, GuiDimensionConstraint dimensionConstraint) {
		this(pixelsSupplier);
		this.dimensionConstraint = Objects.requireNonNull(dimensionConstraint);
	}

	//TODO @JaryJay: explain this
	public PixelPositionConstraint(float pixels, GuiDimensionConstraint dimensionConstraint) {
		this(pixels);
		this.dimensionConstraint = Objects.requireNonNull(dimensionConstraint);
	}

	@Override
	public float get(float start, float end) {
		if (pixelsSupplier != null) {
			pixels = pixelsSupplier.get();
		}
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
