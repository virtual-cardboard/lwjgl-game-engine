package context.input.eventhandler;

import common.event.AbstractGameEventHandler;
import context.input.event.GameInputEvent;

public abstract interface AbstractGameInputEventHandler<T extends GameInputEvent>
		extends AbstractGameEventHandler<T> {

}