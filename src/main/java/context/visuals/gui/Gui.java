package context.visuals.gui;

import java.util.ArrayList;
import java.util.List;

import common.math.Matrix4f;
import context.visuals.GameVisuals;
import context.visuals.gui.constraint.dimension.GuiDimensionConstraint;
import context.visuals.gui.constraint.position.GuiPositionConstraint;
import context.visuals.renderer.GuiRenderer;

/**
 * A rectangular Graphical User Interface (GUI) with position constraints and
 * dimension constraints.
 * <p>
 * A <code>Gui</code> can have any number of children <code>Guis</code>.
 * </p>
 * <p>
 * <code>Guis</code> are recursively displayed by {@link GuiRenderer}, starting
 * with the root Gui.
 * </p>
 * 
 * @author Jay
 * 
 * @see GuiPositionConstraint
 * @see GuiDimensionConstraint
 * @see GameVisuals
 * @see RootGui
 *
 */
public class Gui {

	private GuiPositionConstraint posXConstraint;
	private GuiPositionConstraint posYConstraint;
	private GuiDimensionConstraint widthConstraint;
	private GuiDimensionConstraint heightConstraint;

	private int backgroundColour;
	private boolean enabled = true;

	private Gui parent;
	private List<Gui> children;

	public Gui() {
		children = new ArrayList<>();
	}

	public void addChild(Gui child) {
		child.setParent(this);
		this.children.add(child);
	}

	public void additionalRenderActions(Matrix4f matrix4f) {
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

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
