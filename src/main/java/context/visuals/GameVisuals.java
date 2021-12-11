package context.visuals;

import static context.visuals.colour.Colour.normalizedA;
import static context.visuals.colour.Colour.normalizedB;
import static context.visuals.colour.Colour.normalizedG;
import static context.visuals.colour.Colour.normalizedR;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.function.Consumer;
import java.util.function.Predicate;

import common.event.GameEvent;
import common.loader.GameLoader;
import context.ContextPart;
import context.GLContext;
import context.ResourcePack;
import context.data.GameData;
import context.logic.GameEventHandler;
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
	private RootGui rootGui = new RootGui(0, 0);
	private GameLoader loader;
	private boolean initialized;

	private Queue<GameEvent> in;
	@SuppressWarnings("rawtypes")
	protected Map<Class, List<GameEventHandler>> handlers = new HashMap<>();
	private ResourcePack resourcePack;

	public RootGui rootGui() {
		return rootGui;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public final void handleEvents() {
		while (!in.isEmpty()) {
			GameEvent event = in.poll();
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

	public final void setComponents(Queue<GameEvent> in, GameLoader loader, ResourcePack resourcePack) {
		this.in = in;
		this.loader = loader;
		this.resourcePack = resourcePack;
	}

	public final void doInit() {
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

}
