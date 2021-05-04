package context.visuals;

import context.ContextPart;
import context.data.GameData;
import context.visuals.displayer.Displayable;
import context.visuals.displayer.DisplayerMatcher;

/**
 * A bundle part that displays visuals based on data from {@link GameData}.
 * 
 * @author Jay
 *
 */
public abstract class GameVisuals extends ContextPart {

	private DisplayerMatcher displayerMatcher;

	public void init() {
		this.displayerMatcher = new DisplayerMatcher(getContext().getWrapper().getRenderer());
	}

	public void render() {
		GameData data = getContext().getData();
		for (Displayable d : data.getToBeDisplayed()) {
			displayerMatcher.getDisplayer(d).display(d);
		}
	}

}
