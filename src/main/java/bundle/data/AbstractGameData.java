package bundle.data;

import java.util.ArrayList;
import java.util.List;

import bundle.AbstractBundlePart;
import bundle.data.gui.AbstractGUI;
import bundle.data.gui.InvisibleGUI;
import bundle.visuals.display.Displayable;
import common.coordinates.PixelCoordinates;
import common.entity.User;

/**
 * A bundle part that stores data.
 * 
 * @author Jay
 *
 */
public abstract class AbstractGameData extends AbstractBundlePart {

	private User user = new User("Jay", 0);

	/**
	 * A list of displayables.
	 */
	private List<Displayable> toBeDisplayed = new ArrayList<>();

	/**
	 * The main GUI to which all guis will be descendants of.
	 */
	private AbstractGUI mainGui = new InvisibleGUI("", 0, 0, 0, 0);

	private PixelCoordinates cursorCoordinates;

	public List<Displayable> getToBeDisplayed() {
		return toBeDisplayed;
	}

	public void addGUI(AbstractGUI gui) {
		mainGui.addChild(gui);
	}

	public void addDisplayable(Displayable displayable) {
		toBeDisplayed.add(displayable);
	}

	public AbstractGUI getMainGUI() {
		return mainGui;
	}

	public abstract void init();

	public User getUser() {
		return user;
	}

	public PixelCoordinates getCursorCoordinates() {
		return cursorCoordinates;
	}

	public void setCursorCoordinates(PixelCoordinates cursorCoordinates) {
		this.cursorCoordinates = cursorCoordinates;
	}
}
