package engine.common.event.handling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.function.Consumer;
import java.util.function.Predicate;

import engine.common.event.GameEvent;

/**
 * A group of {@link GameEventHandler GameEventHandlers} that can handle events.
 *
 * @param <T> the specific type of {@link GameEvent} to handle
 * @author Lunkle
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class GameEventHandlerGroup<T extends GameEvent> {

	protected Map<Class, List<GameEventHandler>> handlers = new HashMap<>();

	/**
	 * Handle one event
	 *
	 * @param event
	 */
	public void handleEvent(T event) {
		Class cls = event.getClass();
		for (; !cls.equals(Object.class); cls = cls.getSuperclass()) {
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

	/**
	 * Handle all events available in the queue
	 */
	public void handleEventQueue(Queue<? extends T> eventQueue) {
		while (!eventQueue.isEmpty()) {
			T event = eventQueue.poll();
			handleEvent(event);
		}
	}

	public <R extends T> void addHandler(Class<R> clazz, Predicate<? super R> predicate, Consumer<? super R> consumer, boolean consumes) {
		addHandler(clazz, new GameEventHandler<R>(predicate, consumer, consumes));
	}

	public <R extends T> void addHandler(Class<R> clazz, Consumer<? super R> consumer) {
		addHandler(clazz, new GameEventHandler<R>(consumer));
	}

	public <R extends T> void addHandler(Class<R> clazz, GameEventHandler<? super R> handler) {
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

}
