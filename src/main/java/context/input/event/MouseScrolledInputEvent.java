package context.input.event;

import common.source.GameSource;
import state.user.LocalUser;

public final class MouseScrolledInputEvent extends GameInputEvent {

	private final int amount;

	public MouseScrolledInputEvent(long time, GameSource source, int amount) {
		super(time, source);
		this.amount = amount;
	}

	public MouseScrolledInputEvent(int amount) {
		super(LocalUser.LOCAL_USER);
		this.amount = amount;
	}

	public int getAmount() {
		return amount;
	}

}
