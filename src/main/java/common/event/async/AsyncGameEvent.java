package common.event.async;

import common.event.GameEvent;

public abstract class AsyncGameEvent extends GameEvent {

	private long scheduledTick = 0;
	private AsyncEventCallback callback;

	public AsyncGameEvent(long scheduledTick, AsyncEventCallback callback) {
		this.scheduledTick = scheduledTick;
		this.callback = callback;
	}

	public long scheduledTick() {
		return scheduledTick;
	}

	public AsyncEventCallback callback() {
		return callback;
	}

	public boolean shouldExecute(long currentTick) {
		return currentTick >= scheduledTick;
	}

	public boolean shouldExecuteThisTick(long currentTick) {
		if (currentTick > scheduledTick) {
			throw new RuntimeException("AsyncGameEvent " + this + " missed scheduled execution tick");
		}
		return currentTick == scheduledTick;
	}

}
