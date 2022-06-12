package context.logic;

import java.util.Queue;
import java.util.function.Consumer;
import java.util.function.Predicate;

import context.ContextPart;
import context.data.GameData;
import context.input.GameInput;
import engine.common.QueueGroup;
import engine.common.event.GameEvent;
import engine.common.event.async.AsyncEventPriorityQueue;
import engine.common.event.async.AsyncGameEvent;
import engine.common.event.handling.GameEventHandler;
import engine.common.event.handling.GameEventHandlerGroup;
import engine.common.loader.GameLoader;
import engine.common.timestep.GameLogicTimer;

/**
 * A context part that updates data and handles {@link GameEvent}s.
 *
 * @author Jay
 * @see ContextPart
 * @see GameData
 * @see GameInput
 */
public abstract class GameLogic extends ContextPart {

	private GameLogicTimer timer;
	private GameLoader loader;
	protected boolean timeSensitive = true;

	/**
	 * A counter that is increased every time {@link #update()} is called.
	 */
	private int gameTick = -1;

	private AsyncEventPriorityQueue asyncEventQueue = new AsyncEventPriorityQueue();

	protected GameEventHandlerGroup<GameEvent> handlers = new GameEventHandlerGroup<>();
	private QueueGroup queueGroup;

	public final void setComponents(GameLogicTimer timer, QueueGroup queueGroup, GameLoader loader) {
		this.timer = timer;
		this.queueGroup = queueGroup;
		this.loader = loader;
	}

	public final void doUpdate() {
		gameTick++;
		handlers.handleEventQueue(queueGroup.inputToLogic);
		handleAsyncEvents();
		update();
	}

	private void handleAsyncEvents() {
		while (asyncEventQueue.peek(gameTick) != null) {
			handlers.handleEvent(asyncEventQueue.poll());
		}
	}

	protected void handleEvent(GameEvent event) {
		if (!(event instanceof AsyncGameEvent)) {
			// Handle immediately
			handlers.handleEvent(event);
		} else {
			// Handle later
			AsyncGameEvent asyncEvent = (AsyncGameEvent) event;
			if (asyncEvent.shouldHandle(gameTick)) {
				handlers.handleEvent(asyncEvent);
			} else {
				asyncEventQueue.add(asyncEvent);
			}
		}
	}

	/**
	 * Updates the game. This is called every tick in {@link GameLogicTimer}.
	 */
	public abstract void update();

	protected <T extends GameEvent> void addHandler(Class<T> clazz, Predicate<T> predicate, Consumer<T> consumer, boolean consumes) {
		handlers.addHandler(clazz, predicate, consumer, consumes);
	}

	protected <T extends GameEvent> void addHandler(Class<T> clazz, Consumer<T> consumer) {
		handlers.addHandler(clazz, consumer);
	}

	protected <T extends GameEvent> void addHandler(Class<T> clazz, GameEventHandler<T> handler) {
		handlers.addHandler(clazz, handler);
	}

	protected final GameLoader loader() {
		return loader;
	}

	public QueueGroup queueGroup() {
		return queueGroup;
	}

	public void pushEventToQueueGroup(GameEvent e) {
		queueGroup.pushEventFromLogic(e);
	}

	public void pushAll(Queue<GameEvent> events) {
		while (!events.isEmpty()) {
			GameEvent e = events.poll();
			queueGroup.pushEventFromLogic(e);
		}
	}

	public boolean timeSensitive() {
		return timeSensitive;
	}

	/**
	 * @return the current game tick
	 */
	public int gameTick() {
		return gameTick;
	}

	public void setGameTick(int gameTick) {
		this.gameTick = gameTick;
	}

	public void setFrameRate(float frameRate) {
		timer.setFrameRate(frameRate);
	}

}
