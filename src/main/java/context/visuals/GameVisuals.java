package context.visuals;

import static context.visuals.colour.Colour.normalizedA;
import static context.visuals.colour.Colour.normalizedB;
import static context.visuals.colour.Colour.normalizedG;
import static context.visuals.colour.Colour.normalizedR;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

import common.loader.GameLoader;
import context.ContextPart;
import context.data.GameData;
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

	public RootGui rootGui() {
		return rootGui;
	}

	/**
	 * Uses {@link GameRenderer}s to render the game. This is automatically called
	 * every frame.
	 */
	public abstract void render();

	protected final void background(int colour) {
		glClearColor(normalizedR(colour), normalizedG(colour), normalizedB(colour), normalizedA(colour));
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	public final void setLoader(GameLoader loader) {
		this.loader = loader;
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

}
