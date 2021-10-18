package context.data;

import common.loader.IOLoader;
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

	private IOLoader loader;

	public final void doInit(IOLoader loader) {
		this.loader = loader;
		init();
	}

	public IOLoader loader() {
		return loader;
	}

}
