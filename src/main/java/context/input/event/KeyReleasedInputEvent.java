package context.input.event;

import common.source.GameSource;
import state.user.LocalUser;

public final class KeyReleasedInputEvent extends GameInputEvent {

	private final int keyCode;

	public KeyReleasedInputEvent(long time, GameSource source, int keyCode) {
		super(time, source);
		this.keyCode = keyCode;
	}

	public KeyReleasedInputEvent(int keyCode) {
		super(LocalUser.LOCAL_USER);
		this.keyCode = keyCode;
	}

	public int getKeyCode() {
		return keyCode;
	}

}
