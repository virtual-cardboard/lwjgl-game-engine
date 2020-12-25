package bundle.input.eventhandler;

import bundle.input.event.AbstractGameInputEvent;
import common.event.AbstractGameEventHandler;

public abstract interface AbstractGameInputEventHandler<T extends AbstractGameInputEvent>
		extends AbstractGameEventHandler<T> {

}