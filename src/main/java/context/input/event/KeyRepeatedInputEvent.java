package context.input.event;

import common.source.GameSource;
import context.data.user.LocalUser;

public final class KeyRepeatedInputEvent extends GameInputEvent {

	private static final long serialVersionUID = 486737297198618279L;

	private final int code;

	public KeyRepeatedInputEvent(long time, GameSource source, int code) {
		super(time, source);
		this.code = code;
	}

	public KeyRepeatedInputEvent(int code) {
		super(LocalUser.LOCAL_USER);
		this.code = code;
	}

	public int code() {
		return code;
	}

}
