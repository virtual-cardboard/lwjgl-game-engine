package context.visuals.gui.constraint.position;

import java.util.Objects;

import context.visuals.gui.Anchor;
import context.visuals.gui.constraint.dimension.GuiDimensionConstraint;

public class PixelPositionConstraint extends GuiPositionConstraint {

	private int pixels;
	private Anchor anchor;
	private GuiDimensionConstraint dimensionConstraint;

	public PixelPositionConstraint(int pixels) {
		this(pixels, Anchor.LEADING, null);
	}

	public PixelPositionConstraint(int pixels, Anchor anchor, GuiDimensionConstraint dimensionConstraint) {
		this.pixels = pixels;
		this.anchor = Objects.requireNonNull(anchor);
		this.dimensionConstraint = dimensionConstraint;
	}

	@Override
	public float calculateValue(float start, float end) {
		if (anchor == Anchor.LEADING) {
			return start + pixels;
		}
		return end - pixels - dimensionConstraint.calculateValue(start, end);
	}

	public int getPixels() {
		return pixels;
	}

	public void setPixels(int pixels) {
		this.pixels = pixels;
	}

	public Anchor getAnchor() {
		return anchor;
	}

	public void setAnchorLeading() {
		this.anchor = Anchor.LEADING;
	}

	public void setAnchorTrailing(GuiDimensionConstraint dimensionConstraint) {
		if (dimensionConstraint != null) {
			this.dimensionConstraint = dimensionConstraint;
		}
		this.anchor = Anchor.TRAILING;
	}

}
