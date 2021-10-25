package context.input.event;

import common.source.GameSource;
import context.data.user.LocalUser;

public final class KeyReleasedInputEvent extends GameInputEvent {

	private static final long serialVersionUID = 7134491115053491708L;

	private final int code;

	public KeyReleasedInputEvent(long time, GameSource source, int code) {
		super(time, source);
		this.code = code;
	}

	public KeyReleasedInputEvent(int code) {
		super(LocalUser.LOCAL_USER);
		this.code = code;
	}

	public int keyCode() {
		return code;
	}

}
