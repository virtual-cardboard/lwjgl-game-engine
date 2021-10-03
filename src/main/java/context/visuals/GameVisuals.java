package context.visuals;

import common.loader.Loader;
import context.ContextPart;
import context.data.GameData;
import context.visuals.gui.Gui;
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
	private Loader loader;

	public void addGui(Gui gui) {
		rootGui.addChild(gui);
	}

	public RootGui getRootGui() {
		return rootGui;
	}

	/**
	 * Uses {@link GameRenderer}s to render the game. This is automatically called
	 * every frame.
	 */
	public abstract void render();

	public final void doInit(Loader loader) {
		this.loader = loader;
		init();
	}

	public Loader loader() {
		return loader;
	}

}
