package context.visuals;

import static context.visuals.colour.Colour.normalizedA;
import static context.visuals.colour.Colour.normalizedB;
import static context.visuals.colour.Colour.normalizedG;
import static context.visuals.colour.Colour.normalizedR;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

import java.util.Queue;
import java.util.function.Consumer;
import java.util.function.Predicate;

import context.ContextPart;
import context.GLContext;
import context.data.GameData;
import context.logic.TimeAccumulator;
import context.visuals.gui.RootGui;
import engine.common.ContextQueues;
import engine.common.event.GameEvent;
import engine.common.event.handling.GameEventHandler;
import engine.common.event.handling.GameEventHandlerGroup;
import engine.common.loader.GameLoader;
import engine.common.timestep.WindowFrameUpdater;

/**
 * A bundle part that displays visuals based on data from {@link GameData}.
 *
 * @author Jay
 */
public abstract class GameVisuals extends ContextPart {

	/**
	 * The {@link RootGui} to which all GUIs will be children of.
	 */
	private final RootGui rootGui = new RootGui(0, 0);

	private GameLoader loader;

	private TimeAccumulator logicAccumulator;
	private boolean initialized;

	private Queue<GameEvent> in;
	private final GameEventHandlerGroup<GameEvent> handlers = new GameEventHandlerGroup<>();

	public RootGui rootGui() {
		return rootGui;
	}

	public final void handleEvents() {
		handlers.handleEventQueue(in);
	}

	protected final void background(int colour) {
		glClearColor(normalizedR(colour), normalizedG(colour), normalizedB(colour), normalizedA(colour));
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	/**
	 * Renders the current frame. This is automatically called every frame by the {@link WindowFrameUpdater}.
	 */
	public abstract void render();

	protected <T extends GameEvent> void addHandler(Class<T> clazz, Predicate<T> predicate, Consumer<T> consumer, boolean consumes) {
		handlers.addHandler(clazz, predicate, consumer, consumes);
	}

	protected <T extends GameEvent> void addHandler(Class<T> clazz, Consumer<T> consumer) {
		handlers.addHandler(clazz, consumer);
	}

	protected <T extends GameEvent> void addHandler(Class<T> clazz, GameEventHandler<T> handler) {
		handlers.addHandler(clazz, handler);
	}

	public final void setComponents(ContextQueues contextQueues, GameLoader loader) {
		this.in = contextQueues.logicToVisuals;
		this.loader = loader;
	}

	public final void doInit(TimeAccumulator logicAccumulator) {
		this.logicAccumulator = logicAccumulator;
		init();
		initialized = true;
	}

	/**
	 * Initializes the {@link GameVisuals}. This is called by the {@link WindowFrameUpdater} only once, right after the
	 * context is transitioned and before the calls to {@link #render()}.
	 * <p>
	 * IMPORTANT: By default, this function does nothing. Override its behaviour as needed.
	 */
	@Override
	public void init() {
	}

	public final boolean initialized() {
		return initialized;
	}

	protected final GameLoader loader() {
		return loader;
	}

	protected GLContext glContext() {
		return context().glContext();
	}

	/**
	 * @return the time since the last update tick, in milliseconds
	 */
	public final float deltaTime() {
		return context().logic().timeSinceLastUpdateTime();
	}

}
