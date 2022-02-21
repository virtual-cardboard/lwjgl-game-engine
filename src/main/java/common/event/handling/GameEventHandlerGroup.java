package common.event.handling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.function.Consumer;
import java.util.function.Predicate;

import common.event.GameEvent;

/**
 * A group of {@link GameEventHandler GameEventHandlers} that can handle events.
 * 
 * @author Lunkle
 *
 * @param <T> the specific type of {@link GameEvent} to handle
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
		for (; !cls.getSuperclass().equals(Object.class); cls = cls.getSuperclass()) {
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

	protected void addHandler(Class<T> clazz, Predicate<T> predicate, Consumer<T> consumer, boolean consumes) {
		addHandler(clazz, new GameEventHandler<>(predicate, consumer, consumes));
	}

	protected void addHandler(Class<T> clazz, Consumer<T> consumer) {
		addHandler(clazz, new GameEventHandler<>(consumer));
	}

	private void addHandler(Class<T> clazz, GameEventHandler<T> handler) {
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
