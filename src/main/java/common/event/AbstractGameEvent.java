package common.event;

import common.source.GameSource;

public abstract class AbstractGameEvent {

	private final long time;
	private final GameSource source;

	private int priority = 0;

	public AbstractGameEvent(long time, GameSource source) {
		this.time = time;
		this.source = source;
	}

	public long getTime() {
		return time;
	}

	/**
	 * A getter of the game event's priority level. Greater number means higher
	 * priority.
	 * 
	 * @return the priority level
	 */
	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public GameSource getSource() {
		return source;
	}

}
