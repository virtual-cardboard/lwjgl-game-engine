package context.visuals.gui.constraint.position;

import java.util.Objects;

import context.visuals.gui.Anchor;

public class PixelPositionConstraint extends GuiPositionConstraint {

	private float pixels;
	private Anchor anchor;

	public PixelPositionConstraint(float pixels) {
		this(pixels, Anchor.LEADING);
	}

	public PixelPositionConstraint(float pixels, Anchor anchor) {
		this.pixels = pixels;
		this.anchor = Objects.requireNonNull(anchor);
	}

	@Override
	public float calculateValue(float start, float end) {
		return anchor == Anchor.LEADING ? start + pixels : end - pixels;
	}

	public float getPixels() {
		return pixels;
	}

	public void setPixels(float pixels) {
		this.pixels = pixels;
	}

	public Anchor getAnchor() {
		return anchor;
	}

	public void setAnchorLeading() {
		this.anchor = Anchor.LEADING;
	}

	public void setAnchorTrailing() {
		this.anchor = Anchor.TRAILING;
	}

}
