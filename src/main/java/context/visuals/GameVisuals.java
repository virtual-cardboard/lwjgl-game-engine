package context.visuals;

import context.ContextPart;
import context.data.GameData;
import context.data.gui.GUI;
import context.visuals.displayer.Displayable;
import context.visuals.displayer.DisplayerMatcher;

/**
 * A bundle part that displays visuals based on data from
 * {@link GameData}.
 * 
 * @author Jay
 *
 */
public abstract class GameVisuals extends ContextPart {

	private DisplayerMatcher displayerFactory;

	public void init() {
		this.displayerFactory = new DisplayerMatcher(getBundle().getWrapper().getRenderer());
	}

	public void render() {
		try {
			renderHelper();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void renderHelper() throws ClassNotFoundException {
		displayBeforeDisplayables();
		for (Displayable d : getBundle().getData().getToBeDisplayed()) {
			displayerFactory.getDisplayer(d).display(d);
		}
		displayBeforeGUIS();
		for (GUI gui : getBundle().getData().getMainGUI()) {
			displayerFactory.getDisplayer(gui).display(gui);
		}
		displayAfterGUIS();
	}

	public abstract void displayBeforeDisplayables();

	public abstract void displayBeforeGUIS();

	public abstract void displayAfterGUIS();

}
