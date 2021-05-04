package context.input.event;

import common.source.GameSource;

public class MousePressedInputEvent extends AbstractGameInputEvent {

	private int mouseButton;
	private int mouseX;
	private int mouseY;

	public MousePressedInputEvent(long time, GameSource source, int mouseButton, int mouseX, int mouseY) {
		super(time, source);
		this.mouseButton = mouseButton;
		this.mouseX = mouseX;
		this.mouseY = mouseY;
	}

	public int getMouseButton() {
		return mouseButton;
	}

	public int getMouseX() {
		return mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}

}
