package bundle.visuals;

import bundle.AbstractBundlePart;
import bundle.data.AbstractGameData;
import bundle.data.gui.AbstractGUI;
import bundle.visuals.display.Displayable;
import bundle.visuals.display.DisplayerFactory;

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

	@SuppressWarnings("unchecked")
	public void displayAll() throws ClassNotFoundException {
		displayBeforeDisplayables();
		for (Displayable d : getBundle().getData().getToBeDisplayed()) {
			displayerFactory.getDisplayer(d).display(d);
		}
		displayBeforeGUIS();
		for (AbstractGUI gui : getBundle().getData().getMainGUI()) {
			displayerFactory.getDisplayer(gui).display(gui);
		}
		displayAfterGUIS();
	}

	public abstract void displayBeforeDisplayables();

	public abstract void displayBeforeGUIS();

	public abstract void displayAfterGUIS();

}
