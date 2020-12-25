package bundle.input.event;

import common.source.GameSource;

public class MouseReleasedInputEvent extends AbstractGameInputEvent {

	private int mouseButton;
	private int mouseX;
	private int mouseY;

	public MouseReleasedInputEvent(long time, GameSource source, int mouseButton, int mouseX, int mouseY) {
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

	@Override
	public String getName() {
		return this.getName();
	}

}
