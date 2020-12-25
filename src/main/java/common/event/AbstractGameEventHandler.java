package common.event;

public interface AbstractGameEventHandler<T extends AbstractGameEvent> {

	/**
	 * Handles a game event. Returns true if it consumes the event, false if it
	 * doesn't. Consuming the event means that the event is used up and cannot be
	 * handled anymore by anything else.
	 * 
	 * @param event the game event
	 * @return whether or not the event was consumed
	 */
	public boolean handle(T event);

}
