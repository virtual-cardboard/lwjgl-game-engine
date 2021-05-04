package context.logic;

import java.util.PriorityQueue;

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

	private PriorityQueue<GameEvent> eventQueue;

	public final void init(PriorityQueue<GameEvent> eventQueue) {
		this.eventQueue = eventQueue;
		doInit();
	}

	public void doInit() {
	}

	public abstract void update();

}
