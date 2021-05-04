package context.input.event;

import common.source.GameSource;
import state.user.LocalUser;

public final class MousePressedInputEvent extends GameInputEvent {

	private final int mouseButton;
	private final int mouseX;
	private final int mouseY;

	public MousePressedInputEvent(long time, GameSource source, int mouseButton, int mouseX, int mouseY) {
		super(time, source);
		this.mouseButton = mouseButton;
		this.mouseX = mouseX;
		this.mouseY = mouseY;
	}

	public MousePressedInputEvent(int mouseButton, int mouseX, int mouseY) {
		super(LocalUser.LOCAL_USER);
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
