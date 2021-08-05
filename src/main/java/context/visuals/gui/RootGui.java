package context.visuals.gui;

import common.math.Vector2f;
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

	public int getWidth() {
		PixelDimensionConstraint width = (PixelDimensionConstraint) super.getWidthConstraint();
		return (int) width.getPixels();
	}

	public int getHeight() {
		PixelDimensionConstraint height = (PixelDimensionConstraint) super.getHeightConstraint();
		return (int) height.getPixels();
	}

	public Vector2f getDimensions() {
		return new Vector2f(getWidth(), getHeight());
	}

	public void setDimensions(int width, int height) {
		PixelDimensionConstraint widthConstraint = (PixelDimensionConstraint) getWidthConstraint();
		widthConstraint.setPixels(width);
		PixelDimensionConstraint heightConstraint = (PixelDimensionConstraint) getHeightConstraint();
		heightConstraint.setPixels(height);
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
