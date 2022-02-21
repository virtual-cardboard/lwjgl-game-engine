package context.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.function.Consumer;
import java.util.function.Predicate;

import common.event.GameEvent;
import common.event.async.AsyncEventPriorityQueue;
import common.event.handling.GameEventHandler;
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
@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class GameLogic extends ContextPart {

	private GameLoader loader;
	protected boolean timeSensitive = true;

	/**
	 * A counter that is increased every time {@link #update()} is called.
	 */
	private int gameTick = -1;

	private AsyncEventPriorityQueue asyncEventQueue;
	protected Map<Class, List<GameEventHandler>> asyncEventHandlers = new HashMap<>();

	private Queue<GameEvent> in;
	protected Map<Class, List<GameEventHandler>> handlers = new HashMap<>();
	private Queue<GameEvent> out;

	public final void setComponents(Queue<GameEvent> in, Queue<GameEvent> out, GameLoader loader) {
		this.in = in;
		this.out = out;
		this.loader = loader;
	}

	public final void doUpdate() {
		gameTick++;
		handleInputEvents();
		handleAsyncEvents();
		update();
	}

	protected void handleAsyncEvents() {
		while (asyncEventQueue.peek().shouldExecute(gameTick)) {

		}
	}

	private void handleInputEvents() {
		while (!in.isEmpty()) {
			GameEvent event = in.poll();
			Class<? extends GameEvent> cls = event.getClass();
			for (; !cls.getSuperclass().equals(Object.class); cls = (Class<? extends GameEvent>) cls.getSuperclass()) {
				List<GameEventHandler> list = handlers.get(cls);
				if (list == null) {
					continue;
				}
				for (GameEventHandler handler : list) {
					if (handler.isSatisfiedBy(event)) {
						handler.accept(event);
						if (handler.doesConsume()) {
							break;
						}
					}
				}
			}
		}
	}

	/**
	 * Updates the game. This is called every tick in {@link GameLogicTimer}.
	 */
	public abstract void update();

	protected <T extends GameEvent> void addHandler(Class<T> clazz, Predicate<T> predicate, Consumer<T> consumer, boolean consumes) {
		addHandler(clazz, new GameEventHandler<>(predicate, consumer, consumes));
	}

	protected <T extends GameEvent> void addHandler(Class<T> clazz, Consumer<T> consumer) {
		addHandler(clazz, new GameEventHandler<>(consumer));
	}

	private <T extends GameEvent> void addHandler(Class<T> clazz, GameEventHandler<T> handler) {
		handlers.compute(clazz, (k, v) -> {
			if (v == null) {
				List<GameEventHandler> list = new ArrayList<>();
				list.add(handler);
				return list;
			}
			v.add(handler);
			return v;
		});
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
