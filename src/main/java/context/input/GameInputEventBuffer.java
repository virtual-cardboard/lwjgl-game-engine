package context.input;

import java.util.Queue;
import java.util.TreeSet;
import java.util.concurrent.LinkedBlockingQueue;

import common.event.GameEventTimeComparator;
import context.input.event.GameInputEvent;

/**
 * A queue that temporarily stores input events of the last frame from the
 * client. It will also have a longer memory of the client's inputs for
 * reconciliation with the server state.
 * 
 * @author Jay
 *
 */
public class GameInputEventBuffer {

	private Queue<GameInputEvent> clientInputEventQueue;
	@SuppressWarnings("unused")
	private TreeSet<GameInputEvent> oldClientInputEvents;

	public GameInputEventBuffer() {
		clientInputEventQueue = new LinkedBlockingQueue<>();
		oldClientInputEvents = new TreeSet<GameInputEvent>(new GameEventTimeComparator());
	}

	public GameInputEvent getNext() {
		// Poll from the event queue
		GameInputEvent first = clientInputEventQueue.poll();
		// When we eventually add multiplayer, we will need the old events queue to
		// reconcile state
//		if(first != null) {
//			oldEvents.add(first);
//		}
		return first;
	}

	public void add(GameInputEvent inputEvent) {
		clientInputEventQueue.add(inputEvent);
	}

	public boolean isEmpty() {
		return clientInputEventQueue.isEmpty();
	}

}
