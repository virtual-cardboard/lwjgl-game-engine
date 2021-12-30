package context.input.event;

public final class MouseReleasedInputEvent extends GameInputEvent {

	private final int button;

	public MouseReleasedInputEvent(int mouseButton) {
		this.button = mouseButton;
	}

	public int button() {
		return button;
	}

}
