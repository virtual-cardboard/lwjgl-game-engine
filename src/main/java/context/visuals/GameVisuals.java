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

import common.QueueGroup;
import common.event.GameEvent;
import common.event.handling.GameEventHandler;
import common.event.handling.GameEventHandlerGroup;
import common.loader.GameLoader;
import context.ContextPart;
import context.GLContext;
import context.ResourcePack;
import context.data.GameData;
import context.logic.TimeAccumulator;
import context.visuals.gui.RootGui;
import context.visuals.renderer.GameRenderer;

/**
 * A bundle part that displays visuals based on data from {@link GameData}.
 * 
 * @author Jay
 *
 */
public abstract class GameVisuals extends ContextPart {

	/**
	 * The {@link RootGui} to which all GUIs will be children of.
	 */
	protected RootGui rootGui = new RootGui(0, 0);

	protected GameLoader loader;
	protected ResourcePack resourcePack;

	private TimeAccumulator logicAccumulator;
	private boolean initialized;

	private Queue<GameEvent> in;
	protected GameEventHandlerGroup<GameEvent> handlers = new GameEventHandlerGroup<>();

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
	 * Uses {@link GameRenderer}s to render the game. This is automatically called
	 * every frame.
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

	public final void setComponents(QueueGroup queueGroup, GameLoader loader, ResourcePack resourcePack) {
		this.in = queueGroup.logicToVisuals;
		this.loader = loader;
		this.resourcePack = resourcePack;
	}

	public final void doInit(TimeAccumulator logicAccumulator) {
		this.logicAccumulator = logicAccumulator;
		init();
		initialized = true;
	}

	/**
	 * Initializes the {@link GameVisuals}. This is called by the rendering thread.
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

	protected final ResourcePack resourcePack() {
		return resourcePack;
	}

	protected GLContext glContext() {
		return context().glContext();
	}

	public final float alpha() {
		return logicAccumulator.alpha();
	}

}
