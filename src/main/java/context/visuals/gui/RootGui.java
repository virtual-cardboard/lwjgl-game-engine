package context.visuals.gui;

import java.util.List;

import common.math.PosDim;
import common.math.Vector2f;
import common.math.Vector2i;
import context.visuals.GameVisuals;
import context.visuals.gui.constraint.dimension.GuiDimensionConstraint;
import context.visuals.gui.constraint.dimension.PixelDimensionConstraint;
import context.visuals.gui.constraint.position.GuiPositionConstraint;

/**
 * The main <code>Gui</code> that all other <code>Guis</code> are children of.
 * The {@link GameVisuals} always has one <code>RootGui</code>. Users generally
 * shouldn't ever create another <code>RootGui</code>.
 * 
 * @author Jay
 *
 */
public class RootGui extends InvisibleGui {

	public RootGui(int width, int height) {
		super.setWidth(new PixelDimensionConstraint(width));
		super.setHeight(new PixelDimensionConstraint(height));
	}

	@Override
	public void setParent(Gui parent) {
		throw new RuntimeException("Cannot set parent of the root GUI");
	}

	public int getX() {
		return 0;
	}

	public int getY() {
		return 0;
	}

	/**
	 * @return The width in pixels
	 */
	public int widthPx() {
		PixelDimensionConstraint width = (PixelDimensionConstraint) super.width();
		return (int) width.getPixels();
	}

	/**
	 * @return The height in pixels
	 */
	public int heightPx() {
		PixelDimensionConstraint height = (PixelDimensionConstraint) super.height();
		return (int) height.getPixels();
	}

	/**
	 * @return A new {@link Vector2f} representing the dimensions
	 */
	public Vector2f dimensions() {
		return new Vector2f(widthPx(), heightPx());
	}

	/**
	 * Sets the dimensions.
	 * 
	 * @param width  the new width in pixels
	 * @param height the new height in pixels
	 */
	public void setDimensions(int width, int height) {
		PixelDimensionConstraint widthConstraint = (PixelDimensionConstraint) width();
		widthConstraint.setPixels(width);
		PixelDimensionConstraint heightConstraint = (PixelDimensionConstraint) height();
		heightConstraint.setPixels(height);
	}

	public Gui getHoveredGui(Vector2i coords) {
		return doGetHoveredGui(this, coords);
	}

	private Gui doGetHoveredGui(Gui g, Vector2i coords) {
		List<Gui> children = g.getChildren();
		for (int i = children.size() - 1; i >= 0; i--) {
			Gui c = children.get(i);
			if (c.isEnabled()) {
				Gui hovered = doGetHoveredGui(c, coords);
				if (hovered != null) {
					return hovered;
				}
			}
		}
		PosDim pd = g.posdim();
		if (coords.x >= pd.x && coords.y >= pd.y && coords.x <= pd.x + pd.w && coords.y <= pd.y + pd.h) {
			return g;
		}
		return null;
	}

	/**
	 * @return a {@link PosDim} representing the position and dimension
	 */
	@Override
	public PosDim posdim() {
		return new PosDim(0, 0, widthPx(), heightPx());
	}

	@Override
	public void setPosX(GuiPositionConstraint posXConstraint) {
		throw new RuntimeException("Cannot set posX of rootGui");
	}

	@Override
	public void setPosY(GuiPositionConstraint posYConstraint) {
		throw new RuntimeException("Cannot set posY of rootGui");
	}

	@Override
	public void setWidth(GuiDimensionConstraint widthConstraint) {
		System.err.println("Use setDimensions(int, int) on the rootGui instead.");
		super.setWidth(widthConstraint);
	}

	@Override
	public void setHeight(GuiDimensionConstraint heightConstraint) {
		System.err.println("Use setDimensions(int, int) on the rootGui instead.");
		super.setHeight(heightConstraint);
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public void setEnabled(boolean enabled) {
		System.err.println("Cannot enable or disable rootGui");
	}

	@Override
	public void remove() {
		System.err.println("Cannot remove rootGui");
	}

}
