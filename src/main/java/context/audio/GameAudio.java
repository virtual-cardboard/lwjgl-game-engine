package context.audio;

import java.util.function.Consumer;
import java.util.function.Predicate;

import common.QueueGroup;
import common.event.GameEvent;
import common.event.handling.GameEventHandler;
import common.event.handling.GameEventHandlerGroup;
import common.loader.GameLoader;
import context.ContextPart;

public abstract class GameAudio extends ContextPart {

	private GameLoader loader;

	private QueueGroup queueGroup;
	protected GameEventHandlerGroup<GameEvent> handlers = new GameEventHandlerGroup<>();

	public final void doUpdate() {
		handlers.handleEventQueue(queueGroup.logicToAudio);
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

	public final void setComponents(QueueGroup queueGroup, GameLoader loader) {
		this.queueGroup = queueGroup;
		this.loader = loader;
	}

	public final GameLoader loader() {
		return loader;
	}

}
