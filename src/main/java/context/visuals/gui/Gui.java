package context.visuals.gui;

import java.util.ArrayList;
import java.util.List;

import common.math.Matrix4f;
import common.math.Vector2f;
import context.visuals.gui.constraint.dimension.GuiDimensionConstraint;
import context.visuals.gui.constraint.position.GuiPositionConstraint;

public abstract class Gui {

	private GuiPositionConstraint posXConstraint;
	private GuiPositionConstraint posYConstraint;
	private GuiDimensionConstraint widthConstraint;
	private GuiDimensionConstraint heightConstraint;

	private int backgroundColour;

	private Gui parent;
	private List<Gui> children;

	public Gui() {
		children = new ArrayList<>();
	}

	public void addChild(Gui child) {
		child.setParent(this);
		this.children.add(child);
	}

	public Matrix4f getTranformationMatrix() {
		Matrix4f matrix4f = new Matrix4f();
		matrix4f.translate(new Vector2f(-1, 1));
		return matrix4f;
	}

	public GuiPositionConstraint getPosXConstraint() {
		return posXConstraint;
	}

	public void setPosXConstraint(GuiPositionConstraint posXConstraint) {
		this.posXConstraint = posXConstraint;
	}

	public GuiPositionConstraint getPosYConstraint() {
		return posYConstraint;
	}

	public void setPosYConstraint(GuiPositionConstraint posYConstraint) {
		this.posYConstraint = posYConstraint;
	}

	public GuiDimensionConstraint getWidthConstraint() {
		return widthConstraint;
	}

	public void setWidthConstraint(GuiDimensionConstraint widthConstraint) {
		this.widthConstraint = widthConstraint;
	}

	public GuiDimensionConstraint getHeightConstraint() {
		return heightConstraint;
	}

	public void setHeightConstraint(GuiDimensionConstraint heightConstraint) {
		this.heightConstraint = heightConstraint;
	}

	public int getBackgroundColour() {
		return backgroundColour;
	}

	public void setBackgroundColour(int backgroundColour) {
		this.backgroundColour = backgroundColour;
	}

	public Gui getParent() {
		return parent;
	}

	public void setParent(Gui parent) {
		this.parent = parent;
	}

	public List<Gui> getChildren() {
		return children;
	}

}