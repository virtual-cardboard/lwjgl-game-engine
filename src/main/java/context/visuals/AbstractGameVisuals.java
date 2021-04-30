package context.visuals;

import context.AbstractBundlePart;
import context.data.AbstractGameData;
import context.data.gui.GUI;
import context.visuals.displayer.Displayable;
import context.visuals.displayer.DisplayerFactory;

/**
 * A bundle part that displays visuals based on data from
 * {@link AbstractGameData}.
 * 
 * @author Jay
 *
 */
public abstract class AbstractGameVisuals extends AbstractBundlePart {

	private DisplayerFactory displayerFactory;

	public void init() {
		this.displayerFactory = new DisplayerFactory(getBundle().getWrapper().getRenderer());
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
