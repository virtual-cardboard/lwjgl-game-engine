package context.visuals;

import context.visuals.renderer.gui.GuiRenderer;

/**
 * Default implementation of GameVisuals that doesn't render anything.
 * 
 * @author Jay
 *
 */
public class DefaultGameVisuals extends GameVisuals {

	private GuiRenderer guiRenderer;

	@Override
	public void init() {
		System.out.println("=============\nInitializing guiRenderer\n================");
		this.guiRenderer = new GuiRenderer();
	}

	@Override
	public void render() {
	}

}
