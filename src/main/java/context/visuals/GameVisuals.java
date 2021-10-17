package context.visuals;

import java.util.concurrent.CountDownLatch;

import common.loader.GameLoader;
import context.ContextPart;
import context.data.GameData;
import context.visuals.gui.Gui;
import context.visuals.gui.RootGui;
import context.visuals.lwjgl.GLObjectFactory;
import context.visuals.lwjgl.VertexBufferObject.VertexBufferObjectRawData;
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
	private GLObjectFactory glFactory;

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

	public final void doInit(GameLoader loader, GLObjectFactory glFactory) {
		this.loader = loader;
		this.glFactory = glFactory;
		init();
	}

	public final void createVertexArrayObject(int[] indices, VertexBufferObjectRawData... vboRawData) {
		CountDownLatch cdl = new CountDownLatch(vboRawData.length + 1);
		// TODO
	}

	public GameLoader loader() {
		return loader;
	}

}
