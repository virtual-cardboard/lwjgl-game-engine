package context.data;

import java.util.ArrayList;
import java.util.List;

import context.ContextPart;
import context.data.gui.Gui;
import context.data.gui.InvisibleGUI;
import context.visuals.displayer.Displayable;

/**
 * A context part that stores data.
 * 
 * @author Jay
 *
 */
public abstract class GameData extends ContextPart {

	/**
	 * A list of {@link Displayable}s.
	 */
	private List<Displayable> toBeDisplayed = new ArrayList<>();

	/**
	 * The root GUI to which all guis will be descendants of.
	 */
	private Gui rootGui = new InvisibleGUI(0, 0, 0, 0);

	public abstract void init();

	public List<Displayable> getToBeDisplayed() {
		return toBeDisplayed;
	}

	public void addGUI(Gui gui) {
		rootGui.addChild(gui);
	}

	public void addDisplayable(Displayable displayable) {
		toBeDisplayed.add(displayable);
	}

	public Gui getMainGUI() {
		return rootGui;
	}
}
