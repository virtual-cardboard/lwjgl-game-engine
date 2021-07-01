package context.input.event;

import common.source.GameSource;
import context.data.user.LocalUser;

public final class MouseScrolledInputEvent extends GameInputEvent {

	private final float xAmount;
	private final float yAmount;

	public MouseScrolledInputEvent(long time, GameSource source, float xAmount, float yAmount) {
		super(time, source);
		this.xAmount = xAmount;
		this.yAmount = yAmount;
	}

	public MouseScrolledInputEvent(float xAmount, float yAmount) {
		super(LocalUser.LOCAL_USER);
		this.xAmount = xAmount;
		this.yAmount = yAmount;
	}

	public float getxAmount() {
		return xAmount;
	}

	public float getyAmount() {
		return yAmount;
	}

}
