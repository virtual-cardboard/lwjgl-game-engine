package common.event;

import static java.lang.System.currentTimeMillis;

import common.source.GameSource;

public abstract class GameEvent implements Comparable<GameEvent> {

	private final long time;
	private final GameSource source;

	public GameEvent(long time, GameSource source) {
		this.time = time;
		this.source = source;
	}

	public GameEvent(GameSource source) {
		this(currentTimeMillis(), source);
	}

	public long getTime() {
		return time;
	}

	public GameSource getSource() {
		return source;
	}

	@Override
	public int compareTo(GameEvent other) {
		return Long.compare(this.time, other.time);
	}

}
