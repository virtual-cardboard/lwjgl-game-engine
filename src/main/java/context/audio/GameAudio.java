package context.audio;

import java.util.Queue;
import java.util.function.Consumer;
import java.util.function.Predicate;

import common.event.GameEvent;
import common.event.handling.GameEventHandler;
import common.event.handling.GameEventHandlerGroup;
import common.loader.GameLoader;
import context.ContextPart;

public abstract class GameAudio extends ContextPart {

	private GameLoader loader;

	private Queue<GameEvent> in;
	protected GameEventHandlerGroup<GameEvent> handlers = new GameEventHandlerGroup<>();

	public final void doUpdate() {
		handlers.handleEventQueue(in);
		update();
	}

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

	public final void setComponents(Queue<GameEvent> in, GameLoader loader) {
		this.in = in;
		this.loader = loader;
	}

	public final GameLoader loader() {
		return loader;
	}

}
