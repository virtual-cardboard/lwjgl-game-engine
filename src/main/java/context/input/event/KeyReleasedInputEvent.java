package context.input.event;

public final class KeyReleasedInputEvent extends GameInputEvent {

	private final int code;

	public KeyReleasedInputEvent(int code) {
		this.code = code;
	}

	public int keyCode() {
		return code;
	}

}
