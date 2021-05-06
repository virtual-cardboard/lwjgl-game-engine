package context.input.event;

import state.source.GameSource;
import state.user.LocalUser;

public final class KeyRepeatedInputEvent extends GameInputEvent {

	private final int keyCode;

	public KeyRepeatedInputEvent(long time, GameSource source, int keyCode) {
		super(time, source);
		this.keyCode = keyCode;
	}

	public KeyRepeatedInputEvent(int keyCode) {
		super(LocalUser.LOCAL_USER);
		this.keyCode = keyCode;
	}

	public int getKeyCode() {
		return keyCode;
	}

}
