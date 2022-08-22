package context.logic;

import static java.lang.System.currentTimeMillis;

import java.util.function.Consumer;
import java.util.function.Predicate;

import context.ContextPart;
import context.data.GameData;
import context.input.GameInput;
import engine.common.ContextQueues;
import engine.common.event.GameEvent;
import engine.common.event.async.AsyncEventPriorityQueue;
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
	private long lastUpdateTime = -1;

	private final AsyncEventPriorityQueue asyncEventQueue = new AsyncEventPriorityQueue();

	private final GameEventHandlerGroup<GameEvent> handlers = new GameEventHandlerGroup<>();
	private ContextQueues contextQueues;

	public final void setComponents(GameLogicTimer timer, ContextQueues contextQueues, GameLoader loader) {
		this.timer = timer;
		this.contextQueues = contextQueues;
		this.loader = loader;
	}

	public final void doUpdate() {
		if (!context().data().paused()) {
			gameTick++;
			handlers.handleEventQueue(contextQueues.inputToLogic);
			handleAsyncEvents();
			update();
			lastUpdateTime = currentTimeMillis();
		}
	}

	private void handleAsyncEvents() {
		while (asyncEventQueue.peek(gameTick) != null) {
			handlers.handleEvent(asyncEventQueue.poll());
		}
	}

	protected void handleEvent(GameEvent event) {
		handlers.handleEvent(event);
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

	public ContextQueues contextQueues() {
		return contextQueues;
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

	public AsyncEventPriorityQueue asyncEventQueue() {
		return asyncEventQueue;
	}

	public long timeSinceLastUpdateTime() {
		return currentTimeMillis() - lastUpdateTime;
	}

}
