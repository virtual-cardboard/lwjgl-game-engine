package context.data;

import common.loader.Loader;
import context.ContextPart;

/**
 * A context part that stores data about the game. For example, in a single
 * player platformer game, the <code>GameData</code> would store entities,
 * platforms, the number of lives left the player has, etc.
 * 
 * @author Jay
 *
 */
public abstract class GameData extends ContextPart {

	private Loader loader;

	public final void doInit(Loader loader) {
		this.loader = loader;
		init();
	}

	public Loader loader() {
		return loader;
	}

}
