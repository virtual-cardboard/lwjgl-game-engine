package context.input.event;

import common.event.GameEvent;

public abstract class GameInputEvent extends GameEvent {

	public GameInputEvent() {
	}

	public GameInputEvent(long time) {
		super(time);
	}

}
