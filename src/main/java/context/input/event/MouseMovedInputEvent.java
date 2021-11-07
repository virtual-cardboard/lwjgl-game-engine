package context.input.event;

import common.source.GameSource;
import context.data.user.LocalUser;

public final class MouseMovedInputEvent extends GameInputEvent {

	private static final long serialVersionUID = -5757700918491615989L;

	private final int x;
	private final int y;

	public MouseMovedInputEvent(long time, GameSource source, int mouseX, int mouseY) {
		super(time, source);
		this.x = mouseX;
		this.y = mouseY;
	}

	public MouseMovedInputEvent(int mouseX, int mouseY) {
		super(LocalUser.LOCAL_USER);
		this.x = mouseX;
		this.y = mouseY;
	}

	public int x() {
		return x;
	}

	public int y() {
		return y;
	}

}
