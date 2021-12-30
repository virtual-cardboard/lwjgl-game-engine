package context.input.event;

public final class MousePressedInputEvent extends GameInputEvent {

	private final int button;

	public MousePressedInputEvent(int mouseButton) {
		this.button = mouseButton;
	}

	public int button() {
		return button;
	}

}
