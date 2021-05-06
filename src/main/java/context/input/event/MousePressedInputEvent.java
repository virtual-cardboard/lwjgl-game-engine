package context.input.event;

import state.source.GameSource;
import state.user.LocalUser;

public final class MousePressedInputEvent extends GameInputEvent {

	private final int mouseButton;

	public MousePressedInputEvent(long time, GameSource source, int mouseButton) {
		super(time, source);
		this.mouseButton = mouseButton;
	}

	public MousePressedInputEvent(int mouseButton) {
		super(LocalUser.LOCAL_USER);
		this.mouseButton = mouseButton;
	}

	public int getMouseButton() {
		return mouseButton;
	}

}
