package context.data;

import java.util.ArrayList;
import java.util.List;

import common.coordinates.IntCoordinates;
import context.ContextPart;
import context.data.gui.GUI;
import context.data.gui.InvisibleGUI;
import context.visuals.displayer.Displayable;
import state.entity.User;

/**
 * A context part that stores data.
 * 
 * @author Jay
 *
 */
public abstract class GameData extends ContextPart {

	/**
	 * A list of displayables.
	 */
	private List<Displayable> toBeDisplayed = new ArrayList<>();

	/**
	 * The main GUI to which all guis will be descendants of.
	 */
	private GUI mainGui = new InvisibleGUI("", 0, 0, 0, 0);

	private IntCoordinates cursorCoordinates;

	public List<Displayable> getToBeDisplayed() {
		return toBeDisplayed;
	}

	public void addGUI(GUI gui) {
		mainGui.addChild(gui);
	}

	public void addDisplayable(Displayable displayable) {
		toBeDisplayed.add(displayable);
	}

	public GUI getMainGUI() {
		return mainGui;
	}

	public abstract void init();

	public User getUser() {
		return user;
	}

	public IntCoordinates getCursorCoordinates() {
		return cursorCoordinates;
	}

	public void setCursorCoordinates(IntCoordinates cursorCoordinates) {
		this.cursorCoordinates = cursorCoordinates;
	}
}
