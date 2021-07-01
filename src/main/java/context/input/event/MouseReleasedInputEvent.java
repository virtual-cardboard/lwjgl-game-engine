package context.input.event;

import common.source.GameSource;
import context.data.user.LocalUser;

public final class MouseReleasedInputEvent extends GameInputEvent {

	private final int mouseButton;

	public MouseReleasedInputEvent(long time, GameSource source, int mouseButton) {
		super(time, source);
		this.mouseButton = mouseButton;
	}

	public MouseReleasedInputEvent(int mouseButton) {
		super(LocalUser.LOCAL_USER);
		this.mouseButton = mouseButton;
	}

	public int getMouseButton() {
		return mouseButton;
	}

}
