package common.event;

public abstract class DelayedGameEvent extends GameEvent {

	private DelayedEventCallback callback;

	public DelayedGameEvent(DelayedEventCallback callback) {
		this.callback = callback;
	}

	public DelayedGameEvent(long time) {
		super(time);
	}

	public DelayedEventCallback callback() {
		return callback;
	}

}
