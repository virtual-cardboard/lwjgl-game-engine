package context.logic;

import java.util.Queue;

import common.event.GameEvent;
import common.loader.GameLoader;
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
	private GameLoader loader;

	public final void doInit(Queue<GameEvent> eventQueue, GameLoader loader) {
		this.eventQueue = eventQueue;
		this.loader = loader;
		init();
	}

	/**
	 * Updates the game. This is called every tick in {@link GameLogicTimer}.
	 */
	public abstract void update();

	protected final GameLoader loader() {
		return loader;
	}

	protected final Queue<GameEvent> eventQueue() {
		return eventQueue;
	}

}
