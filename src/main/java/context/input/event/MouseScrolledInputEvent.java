package context.input.event;

import common.source.GameSource;

public class MouseScrolledInputEvent extends AbstractGameInputEvent {

	private int amount;

	public MouseScrolledInputEvent(long time, GameSource source, int amount) {
		super(time, source);
		this.amount = amount;
	}

	public int getAmount() {
		return amount;
	}

	@Override
	public String getName() {
		return this.getName();
	}

}
