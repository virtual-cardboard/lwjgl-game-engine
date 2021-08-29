package context.visuals.gui;

import java.util.ArrayList;
import java.util.List;

import context.visuals.GameVisuals;
import context.visuals.gui.constraint.dimension.GuiDimensionConstraint;
import context.visuals.gui.constraint.position.GuiPositionConstraint;
import context.visuals.gui.renderer.DefaultGuiRenderer;
import context.visuals.gui.renderer.GuiRenderer;
import context.visuals.lwjgl.ShaderProgram;
import context.visuals.lwjgl.VertexArrayObject;

/**
 * A Graphical User Interface (GUI) with position constraints and dimension
 * constraints.
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
	private List<Gui> children = new ArrayList<>();

	@SuppressWarnings("rawtypes")
	private GuiRenderer guiRenderer;

	public Gui(ShaderProgram guiShaderProgram, VertexArrayObject rectangleVao) {
		this(new DefaultGuiRenderer(guiShaderProgram, rectangleVao));
	}

	public <T extends Gui> Gui(GuiRenderer<T> guiRenderer) {
		this.guiRenderer = guiRenderer;
	}

	public void addChild(Gui child) {
		child.setParent(this);
		this.children.add(child);
	}

	public void remove() {
		parent.getChildren().remove(this);
	}

	public void clearChildren() {
		children.clear();
	}

	// Getters and setters

	public GuiPositionConstraint getPosX() {
		return posXConstraint;
	}

	public void setPosX(GuiPositionConstraint posXConstraint) {
		this.posXConstraint = posXConstraint;
	}

	public GuiPositionConstraint getPosY() {
		return posYConstraint;
	}

	public void setPosY(GuiPositionConstraint posYConstraint) {
		this.posYConstraint = posYConstraint;
	}

	public GuiDimensionConstraint getWidth() {
		return widthConstraint;
	}

	public void setWidth(GuiDimensionConstraint widthConstraint) {
		this.widthConstraint = widthConstraint;
	}

	public GuiDimensionConstraint getHeight() {
		return heightConstraint;
	}

	public void setHeight(GuiDimensionConstraint heightConstraint) {
		this.heightConstraint = heightConstraint;
	}

	public int getBackgroundColour() {
		return backgroundColour;
	}

	public void setBackground(int backgroundColour) {
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

	@SuppressWarnings("rawtypes")
	public GuiRenderer getGuiRenderer() {
		return guiRenderer;
	}

}
