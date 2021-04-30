package context.input.event;

import common.source.GameSource;

public class KeyReleasedInputEvent extends AbstractGameInputEvent {

	private int keyCode;

	public KeyReleasedInputEvent(long time, GameSource source, int keyCode) {
		super(time, source);
		this.keyCode = keyCode;
	}

	public int getKeyCode() {
		return keyCode;
	}

	@Override
	public String getName() {
		return this.getName();
	}

}
