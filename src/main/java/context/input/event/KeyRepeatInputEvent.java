package context.input.event;

import common.source.GameSource;
import state.user.LocalUser;

public final class KeyRepeatInputEvent extends GameInputEvent {

	private final int keyCode;

	public KeyRepeatInputEvent(long time, GameSource source, int keyCode) {
		super(time, source);
		this.keyCode = keyCode;
	}

	public KeyRepeatInputEvent(int keyCode) {
		super(LocalUser.LOCAL_USER);
		this.keyCode = keyCode;
	}

	public int getKeyCode() {
		return keyCode;
	}

}
