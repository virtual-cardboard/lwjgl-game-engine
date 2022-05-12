package engine.common.event;

import static java.lang.System.currentTimeMillis;

public abstract class GameEvent implements Comparable<GameEvent> {

	protected long time;

	public GameEvent(long time) {
		this.time = time;
	}

	public GameEvent() {
		this(currentTimeMillis());
	}

	protected void setTime(long time) {
		this.time = time;
	}

	public long time() {
		return time;
	}

	@Override
	public int compareTo(GameEvent other) {
		return Long.compare(this.time, other.time);
	}

}
