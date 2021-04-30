package context.data.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import common.DoNothingRunnable;
import common.coordinates.PixelCoordinates;
import common.physics.HasPositionDimensions;
import context.logic.Clickable;
import context.logic.Hoverable;
import context.visuals.displayer.Displayable;
import context.visuals.displayer.GUIDisplayer;

public abstract class GUI implements Displayable, Clickable, Hoverable, HasPositionDimensions, Iterable<GUI> {

	private String text = "";
	private float x;
	private float y;
	private float width;
	private float height;
	private int backgroundR = 255;
	private int backgroundG = 255;
	private int backgroundB = 255;
	private int outlineR = 0;
	private int outlineG = 0;
	private int outlineB = 0;
	private int textR = 0;
	private int textG = 0;
	private int textB = 0;
	private int textSize = 24;

	private boolean hovered = false;
	private boolean pressed = false;

	private Runnable onPress;
	private Runnable onRelease;
	private Runnable onClick;
	private Runnable onHover;
	private Runnable onDehover;

	private GUI parentGUI;
	private List<GUI> children;

	private boolean enabled;

	public GUI(String text, int X, int Y, int width, int height) {
		super();
		this.text = text;
		this.x = X;
		this.y = Y;
		this.width = width;
		this.height = height;
		onPress = new DoNothingRunnable();
		onRelease = new DoNothingRunnable();
		onClick = new DoNothingRunnable();
		onHover = new DoNothingRunnable();
		onDehover = new DoNothingRunnable();

		children = new ArrayList<>();
		enabled = true;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public float getX() {
		return x;
	}

	public void setX(float X) {
		this.x = X;
	}

	public float getY() {
		return y;
	}

	public void setY(float Y) {
		this.y = Y;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public int getBackgroundR() {
		return backgroundR;
	}

	public void setBackgroundR(int backgroundR) {
		this.backgroundR = backgroundR;
	}

	public int getBackgroundG() {
		return backgroundG;
	}

	public void setBackgroundG(int backgroundG) {
		this.backgroundG = backgroundG;
	}

	public int getBackgroundB() {
		return backgroundB;
	}

	public void setBackgroundB(int backgroundB) {
		this.backgroundB = backgroundB;
	}

	public int getOutlineR() {
		return outlineR;
	}

	public void setOutlineR(int outlineR) {
		this.outlineR = outlineR;
	}

	public int getOutlineG() {
		return outlineG;
	}

	public void setOutlineG(int outlineG) {
		this.outlineG = outlineG;
	}

	public int getOutlineB() {
		return outlineB;
	}

	public void setOutlineB(int outlineB) {
		this.outlineB = outlineB;
	}

	public int getTextR() {
		return textR;
	}

	public void setTextR(int textR) {
		this.textR = textR;
	}

	public int getTextG() {
		return textG;
	}

	public void setTextG(int textG) {
		this.textG = textG;
	}

	public int getTextB() {
		return textB;
	}

	public void setTextB(int textB) {
		this.textB = textB;
	}

	public int getTextSize() {
		return textSize;
	}

	public void setTextSize(int textSize) {
		this.textSize = textSize;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void enable() {
		this.enabled = true;
	}

	public void disable() {
		this.enabled = false;
		for (GUI child : children) {
			child.disable();
		}
	}

	public boolean isHovered() {
		return hovered;
	}

	public void setHovered(boolean hovered) {
		this.hovered = hovered;
	}

	public boolean isPressed() {
		return pressed;
	}

	public void setPressed(boolean pressed) {
		this.pressed = pressed;
	}

	public GUI getParent() {
		return parentGUI;
	}

	public void setParent(GUI parent) {
		this.parentGUI = parent;
	}

	public List<GUI> getChildren() {
		return new ArrayList<>(children);
	}

	protected List<GUI> getChildrenArray() {
		return children;
	}

	public void addChild(GUI child) {
		children.add(child);
		child.setParent(this);
	}

	public void removeChild(GUI child) {
		children.remove(child);
	}

	@Override
	public boolean isOn(float x, float y) {
		boolean inboundsX = x > getX() && x < getX() + getWidth();
		boolean inboundsY = y > getY() && y < getY() + getHeight();
		return inboundsX && inboundsY;
	}

	@Override
	public final void onPress() {
		if (!pressed) {
			pressed = true;
			onPress.run();
		}
	}

	@Override
	public Runnable getOnPress() {
		return onPress;
	}

	public void setOnPress(Runnable onPress) {
		this.onPress = onPress;
	}

	public void depressAll() {
		pressed = false;
		for (GUI gui : children) {
			gui.depressAll();
		}
	}

	@Override
	public final void onRelease() {
		if (pressed) {
			onClick.run();
			pressed = false;
		}
		onRelease.run();
	}

	public void releaseAll() {
		onRelease();
		for (GUI gui : children) {
			gui.releaseAll();
		}
	}

	@Override
	public Runnable getOnRelease() {
		return onRelease;
	}

	public void setOnRelease(Runnable onRelease) {
		this.onRelease = onRelease;
	}

	@Override
	public Runnable getOnClick() {
		return onClick;
	}

	public void setOnClick(Runnable onClick) {
		this.onClick = onClick;
	}

	@Override
	public final void onHover() {
		if (!hovered) {
			hovered = true;
			onHover.run();
		}
	}

	@Override
	public Runnable getOnHover() {
		return onHover;
	}

	public void setOnHover(Runnable onHover) {
		this.onHover = onHover;
	}

	@Override
	public final void onDehover() {
		if (hovered) {
			hovered = false;
			onDehover.run();
		}
	}

	public void dehoverAll() {
		onDehover();
		for (GUI gui : children) {
			gui.dehoverAll();
		}
	}

	@Override
	public Runnable getOnDehover() {
		return onDehover;
	}

	public void setOnDehover(Runnable onDehover) {
		this.onDehover = onDehover;
	}

	@Override
	public String getDisplayerName() {
		return GUIDisplayer.class.getName();
	}

	@Override
	public PixelCoordinates getPosition() {
		return new PixelCoordinates(x, y);
	}

	@Override
	public PixelCoordinates getDimensions() {
		return new PixelCoordinates(width, height);
	}

	@Override
	public void setPosition(PixelCoordinates coordinates) {
		x = coordinates.x;
		y = coordinates.y;
	}

	@Override
	public void setDimensions(PixelCoordinates dimensions) {
		width = dimensions.x;
		height = dimensions.y;
	}

	@Override
	public Iterator<GUI> iterator() {
		return new GUIPrefixIterator(this);
	}

	public int getNumChildren() {
		return children.size();
	}

}
