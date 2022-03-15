package context.visuals.gui;

import java.util.ArrayList;
import java.util.List;

import common.math.Matrix4f;
import common.math.PosDim;
import common.math.Vector2f;
import common.math.Vector2i;
import context.GLContext;
import context.visuals.GameVisuals;
import context.visuals.gui.constraint.dimension.GuiDimensionConstraint;
import context.visuals.gui.constraint.position.GuiPositionConstraint;

/**
 * A Graphical User Interface (GUI) with position constraints and dimension
 * constraints.
 * <p>
 * A <code>Gui</code> can have any number of children <code>Guis</code>.
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
public abstract class Gui {

	private GuiPositionConstraint posXConstraint;
	private GuiPositionConstraint posYConstraint;
	private GuiDimensionConstraint widthConstraint;
	private GuiDimensionConstraint heightConstraint;

	private boolean enabled = true;

	private Gui parent;
	private List<Gui> children = new ArrayList<>();

	public abstract void render(GLContext glContext, float x, float y, float width, float height);

	protected final Matrix4f rectToPixelMatrix4f(Vector2i windowDim) {
		return new Matrix4f().translate(new Vector2f(-1, 1)).scale(2f / windowDim.x, -2f / windowDim.y);
	}

	public void addChild(Gui child) {
		child.setParent(this);
		this.children.add(child);
	}

	public void addChild(int i, Gui child) {
		child.setParent(this);
		this.children.add(i, child);
	}

	public void remove() {
		parent.getChildren().remove(this);
		parent = null;
	}

	public void clearChildren() {
		children.clear();
	}

	/**
	 * Gets the absolute position and dimensions.
	 * 
	 * @return a {@link PosDim} containing the position and dimensions
	 */
	public PosDim posdim() {
		PosDim p = parent.posdim();
		return new PosDim(posXConstraint.calculateValue(p.x, p.x + p.w), posYConstraint.calculateValue(p.y, p.y + p.h),
				widthConstraint.calculateValue(p.x, p.x + p.w), heightConstraint.calculateValue(p.y, p.y + p.h));
	}

	// Getters and setters

	public GuiPositionConstraint posX() {
		return posXConstraint;
	}

	public void setPosX(GuiPositionConstraint posXConstraint) {
		this.posXConstraint = posXConstraint;
	}

	public GuiPositionConstraint posY() {
		return posYConstraint;
	}

	public void setPosY(GuiPositionConstraint posYConstraint) {
		this.posYConstraint = posYConstraint;
	}

	public GuiDimensionConstraint width() {
		return widthConstraint;
	}

	public void setWidth(GuiDimensionConstraint widthConstraint) {
		this.widthConstraint = widthConstraint;
	}

	public GuiDimensionConstraint height() {
		return heightConstraint;
	}

	public void setHeight(GuiDimensionConstraint heightConstraint) {
		this.heightConstraint = heightConstraint;
	}

	public Gui parent() {
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
