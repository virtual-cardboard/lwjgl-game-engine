package context.logic;

import java.util.Queue;

import common.event.GameEvent;
import common.timestep.GameLogicTimer;
import context.ContextPart;
import context.data.GameData;
import context.input.GameInput;

/**
 * A context part that updates data and handles {@link GameEvent}s.
 * 
 * @see ContextPart
 * @see GameData
 * @see GameInput
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

	/**
	 * Updates the game. This is called every tick in {@link GameLogicTimer}.
	 */
	public abstract void update();

	protected final Queue<GameEvent> getEventQueue() {
		return eventQueue;
	}

}
