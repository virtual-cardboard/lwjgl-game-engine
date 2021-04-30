package context.input;

import java.util.Queue;
import java.util.TreeSet;
import java.util.concurrent.LinkedBlockingQueue;

import common.event.GameEventTimeComparator;
import context.input.event.AbstractGameInputEvent;

/**
 * A queue that temporarily stores input events of the last frame from the
 * client. It will also have a longer memory of the client's inputs for
 * reconciliation with the server state.
 * 
 * @author Jay
 *
 */
public class GameInputBuffer {

	private Queue<AbstractGameInputEvent> clientInputEventQueue;
	@SuppressWarnings("unused")
	private TreeSet<AbstractGameInputEvent> oldClientInputEvents;

	public GameInputBuffer() {
		clientInputEventQueue = new LinkedBlockingQueue<>();
		oldClientInputEvents = new TreeSet<AbstractGameInputEvent>(new GameEventTimeComparator());
	}

	public AbstractGameInputEvent getNext() {
		// Poll from the event queue
		AbstractGameInputEvent first = clientInputEventQueue.poll();
		// When we eventually add multiplayer, we will need the old events queue to
		// reconcile state
//		if(first != null) {
//			oldEvents.add(first);
//		}
		return first;
	}

	public void add(AbstractGameInputEvent inputEvent) {
		clientInputEventQueue.add(inputEvent);
	}

	public boolean isEmpty() {
		return clientInputEventQueue.isEmpty();
	}

}
