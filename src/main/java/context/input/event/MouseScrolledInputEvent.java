package context.input.event;

public final class MouseScrolledInputEvent extends GameInputEvent {

	private final float xAmount;
	private final float yAmount;

	public MouseScrolledInputEvent(float xAmount, float yAmount) {
		this.xAmount = xAmount;
		this.yAmount = yAmount;
	}

	public float xAmount() {
		return xAmount;
	}

	public float yAmount() {
		return yAmount;
	}

}
