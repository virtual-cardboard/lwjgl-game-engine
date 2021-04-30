package context.input.eventhandler;

import common.event.AbstractGameEventHandler;
import context.input.event.AbstractGameInputEvent;

public abstract interface AbstractGameInputEventHandler<T extends AbstractGameInputEvent>
		extends AbstractGameEventHandler<T> {

}