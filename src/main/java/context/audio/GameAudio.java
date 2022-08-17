package context.audio;

import java.util.function.Consumer;
import java.util.function.Predicate;

import context.ContextPart;
import engine.common.ContextQueues;
import engine.common.event.GameEvent;
import engine.common.event.handling.GameEventHandler;
import engine.common.event.handling.GameEventHandlerGroup;
import engine.common.loader.GameLoader;

public abstract class GameAudio extends ContextPart {

	private GameLoader loader;

	private ContextQueues contextQueues;
	protected GameEventHandlerGroup<GameEvent> handlers = new GameEventHandlerGroup<>();

	public final void doUpdate() {
		handlers.handleEventQueue(contextQueues.logicToAudio);
		update();
	}

	public abstract void update();

	protected <T extends GameEvent> void addHandler(Class<T> clazz, Predicate<? super T> predicate, Consumer<? super T> consumer, boolean consumes) {
		handlers.addHandler(clazz, predicate, consumer, consumes);
	}

	protected <T extends GameEvent> void addHandler(Class<T> clazz, Consumer<? super T> consumer) {
		handlers.addHandler(clazz, consumer);
	}

	protected <T extends GameEvent> void addHandler(Class<T> clazz, GameEventHandler<? super T> handler) {
		handlers.addHandler(clazz, handler);
	}

	public final void setComponents(ContextQueues contextQueues, GameLoader loader) {
		this.contextQueues = contextQueues;
		this.loader = loader;
	}

	public final GameLoader loader() {
		return loader;
	}

}
