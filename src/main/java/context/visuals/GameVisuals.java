package context.visuals;

import context.ContextPart;
import context.data.GameData;
import context.visuals.gui.Gui;
import context.visuals.gui.InvisibleGUI;

/**
 * A bundle part that displays visuals based on data from {@link GameData}.
 * 
 * @author Jay
 *
 */
public abstract class GameVisuals extends ContextPart {

	/**
	 * The root GUI to which all guis will be descendants of.
	 */
	private Gui rootGui = new InvisibleGUI(0, 0, 0, 0);

	public void addGui(Gui gui) {
		rootGui.addChild(gui);
	}

	public Gui getRootGui() {
		return rootGui;
	}

	public void init() {
	}

	public void render() {
	}

}
