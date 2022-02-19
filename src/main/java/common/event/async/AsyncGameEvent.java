package common.event.async;

import common.event.GameEvent;

public abstract class AsyncGameEvent extends GameEvent {

	private AsyncEventCallback callback;

	public AsyncGameEvent(AsyncEventCallback callback) {
		this.callback = callback;
	}

	public AsyncGameEvent(long time) {
		super(time);
	}

	public AsyncEventCallback callback() {
		return callback;
	}

}
