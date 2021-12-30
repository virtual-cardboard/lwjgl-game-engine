package context.input.event;

public final class KeyRepeatedInputEvent extends GameInputEvent {

	private final int code;

	public KeyRepeatedInputEvent(int code) {
		this.code = code;
	}

	public int code() {
		return code;
	}

}
