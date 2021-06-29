package context.visuals.gui;

import context.visuals.gui.constraint.dimension.GuiDimensionConstraint;
import context.visuals.gui.constraint.dimension.PixelDimensionConstraint;
import context.visuals.gui.constraint.position.GuiPositionConstraint;
import context.visuals.gui.constraint.position.PixelPositionConstraint;

public class RootGui extends InvisibleGui {

	public RootGui(int width, int height) {
		setPosXConstraint(new PixelPositionConstraint(0));
		setPosYConstraint(new PixelPositionConstraint(0));
		setWidthConstraint(new PixelDimensionConstraint(width));
		setHeightConstraint(new PixelDimensionConstraint(height));
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
		return width.getPixels();
	}

	public int getHeight() {
		PixelDimensionConstraint height = (PixelDimensionConstraint) super.getHeightConstraint();
		return height.getPixels();
	}

	public void setDimensions(int width, int height) {
		PixelDimensionConstraint widthConstraint = (PixelDimensionConstraint) getWidthConstraint();
		widthConstraint.setPixels(width);
		PixelDimensionConstraint heightConstraint = (PixelDimensionConstraint) getHeightConstraint();
		heightConstraint.setPixels(height);
	}

	@Override
	public void setPosXConstraint(GuiPositionConstraint posXConstraint) {
		throw new RuntimeException("Cannot set posX of rootGui");
	}

	@Override
	public void setPosYConstraint(GuiPositionConstraint posYConstraint) {
		throw new RuntimeException("Cannot set posY of rootGui");
	}

	@Override
	public void setWidthConstraint(GuiDimensionConstraint widthConstraint) {
		System.err.println("Use setDimensions(int, int) on the rootGui instead.");
		super.setWidthConstraint(widthConstraint);
	}

	@Override
	public void setHeightConstraint(GuiDimensionConstraint heightConstraint) {
		System.err.println("Use setDimensions(int, int) on the rootGui instead.");
		super.setHeightConstraint(heightConstraint);
	}

	@Override
	public void setBackgroundColour(int backgroundColour) {
		throw new RuntimeException("Cannot set background colour of rootGui");
	}

	@Override
	public int getBackgroundColour() {
		return 0;
	}

}
