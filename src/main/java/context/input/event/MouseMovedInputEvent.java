package context.input.event;

public final class MouseMovedInputEvent extends GameInputEvent {

	private final int x;
	private final int y;

	public MouseMovedInputEvent(int mouseX, int mouseY) {
		this.x = mouseX;
		this.y = mouseY;
	}

	public int x() {
		return x;
	}

	public int y() {
		return y;
	}

}
