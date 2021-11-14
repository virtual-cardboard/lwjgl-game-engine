package context.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.function.Consumer;
import java.util.function.Predicate;

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
	protected boolean timeSensitive = true;

	@SuppressWarnings("rawtypes")
	protected Map<Class, List<GameEventHandler>> handlers;

	public GameLogic() {
		handlers = new HashMap<>();
	}

	public final void doInit(Queue<GameEvent> eventQueue, GameLoader loader) {
		this.eventQueue = eventQueue;
		this.loader = loader;
		init();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public final void doUpdate() {
		while (!eventQueue().isEmpty()) {
			GameEvent event = eventQueue().poll();
			List<GameEventHandler> list = handlers.get(event.getClass());
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
		update();
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
				@SuppressWarnings("rawtypes")
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

	protected final Queue<GameEvent> eventQueue() {
		return eventQueue;
	}

	public boolean timeSensitive() {
		return timeSensitive;
	}

}
