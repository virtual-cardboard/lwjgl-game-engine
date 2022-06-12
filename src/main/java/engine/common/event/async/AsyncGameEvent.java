package engine.common.event.async;

import engine.common.event.GameEvent;

public abstract class AsyncGameEvent extends GameEvent {

	private long scheduledTick = 0;

	public AsyncGameEvent(long scheduledTick) {
		this.scheduledTick = scheduledTick;
	}

	public long scheduledTick() {
		return scheduledTick;
	}

	public boolean shouldHandle(long currentTick) {
		return currentTick >= scheduledTick;
	}

	public boolean shouldHandleThisTick(long currentTick) {
		if (currentTick > scheduledTick) {
			throw new RuntimeException("AsyncGameEvent " + this + " missed scheduled execution tick");
		}
		return currentTick == scheduledTick;
	}

}
