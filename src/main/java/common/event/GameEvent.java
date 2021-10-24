package common.event;

import static java.lang.System.currentTimeMillis;

import java.io.Serializable;

import common.source.GameSource;

public abstract class GameEvent implements Comparable<GameEvent>, Serializable {

	private static final long serialVersionUID = -5963087341962350911L;

	private final long time;
	private transient final GameSource source;

	public GameEvent(long time, GameSource source) {
		this.time = time;
		this.source = source;
	}

	public GameEvent(GameSource source) {
		this(currentTimeMillis(), source);
	}

	public long time() {
		return time;
	}

	public GameSource source() {
		return source;
	}

	@Override
	public int compareTo(GameEvent other) {
		return Long.compare(this.time, other.time);
	}

}
