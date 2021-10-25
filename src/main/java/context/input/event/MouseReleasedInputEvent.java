package context.input.event;

import common.source.GameSource;
import context.data.user.LocalUser;

public final class MouseReleasedInputEvent extends GameInputEvent {

	private static final long serialVersionUID = -7652238176707641764L;

	private final int button;

	public MouseReleasedInputEvent(long time, GameSource source, int mouseButton) {
		super(time, source);
		this.button = mouseButton;
	}

	public MouseReleasedInputEvent(int mouseButton) {
		super(LocalUser.LOCAL_USER);
		this.button = mouseButton;
	}

	public int button() {
		return button;
	}

}
