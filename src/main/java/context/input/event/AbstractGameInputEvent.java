package context.input.event;

import common.event.AbstractGameEvent;
import common.source.GameSource;

public abstract class AbstractGameInputEvent extends AbstractGameEvent {

	public AbstractGameInputEvent(long time, GameSource source) {
		super(time, source);
	}

	public abstract String getName();

}
