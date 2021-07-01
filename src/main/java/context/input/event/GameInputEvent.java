package context.input.event;

import common.event.GameEvent;
import common.source.GameSource;

public abstract class GameInputEvent extends GameEvent {

	public GameInputEvent(long time, GameSource source) {
		super(time, source);
	}

	public GameInputEvent(GameSource source) {
		this(System.currentTimeMillis(), source);
	}

}
