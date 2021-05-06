package context.logic;

import java.util.Queue;

import common.event.GameEvent;
import context.ContextPart;
import context.data.GameData;
import context.input.GameInput;

/**
 * A bundle part that updates data and handles user input.
 * 
 * See {@link GameData} and {@link GameInput}.
 * 
 * @author Jay
 *
 */
public abstract class GameLogic extends ContextPart {

	private Queue<GameEvent> eventQueue;

	public final void init(Queue<GameEvent> eventQueue) {
		this.eventQueue = eventQueue;
		doInit();
	}

	protected final Queue<GameEvent> getEventQueue() {
		return eventQueue;
	}

	public abstract void update();

}
