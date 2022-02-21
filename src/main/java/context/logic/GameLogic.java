package context.logic;

import java.util.Queue;
import java.util.function.Consumer;
import java.util.function.Predicate;

import common.event.GameEvent;
import common.event.async.AsyncEventPriorityQueue;
import common.event.async.AsyncGameEvent;
import common.event.handling.GameEventHandler;
import common.event.handling.GameEventHandlerGroup;
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

	private GameLoader loader;
	protected boolean timeSensitive = true;

	/**
	 * A counter that is increased every time {@link #update()} is called.
	 */
	private int gameTick = -1;

	private AsyncEventPriorityQueue asyncEventQueue = new AsyncEventPriorityQueue();
	protected GameEventHandlerGroup<AsyncGameEvent> asyncEventHandlers = new GameEventHandlerGroup<>();

	private Queue<GameEvent> in;
	protected GameEventHandlerGroup<GameEvent> handlers = new GameEventHandlerGroup<>();
	private Queue<GameEvent> out;

	public final void setComponents(Queue<GameEvent> in, Queue<GameEvent> out, GameLoader loader) {
		this.in = in;
		this.out = out;
		this.loader = loader;
	}

	public final void doUpdate() {
		gameTick++;
		handlers.handleEventQueue(in);
		handleAsyncEvents();
		update();
	}

	protected void handleAsyncEvents() {
		while (!asyncEventQueue.isEmpty() && asyncEventQueue.peek().shouldExecute(gameTick)) {
			AsyncGameEvent event = asyncEventQueue.poll();
			asyncEventHandlers.handleEvent(event);
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

	public void pushEvent(GameEvent event) {
		out.add(event);
	}

	public void pushAll(Queue<GameEvent> events) {
		while (!events.isEmpty()) {
			out.add(events.poll());
		}
		events.clear();
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

}
