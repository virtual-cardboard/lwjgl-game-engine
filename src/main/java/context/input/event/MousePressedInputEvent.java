package context.input.event;

import common.source.GameSource;
import context.data.user.LocalUser;

public final class MousePressedInputEvent extends GameInputEvent {

	private static final long serialVersionUID = -7530138323230857576L;

	private final int button;

	public MousePressedInputEvent(long time, GameSource source, int mouseButton) {
		super(time, source);
		this.button = mouseButton;
	}

	public MousePressedInputEvent(int mouseButton) {
		super(LocalUser.LOCAL_USER);
		this.button = mouseButton;
	}

	public int button() {
		return button;
	}

}
