package context.data;

import context.ContextPart;
import engine.common.loader.GameLoader;

/**
 * A context part that stores data about the game. For example, in a single
 * player platformer game, the <code>GameData</code> would store entities,
 * platforms, the number of lives left the player has, etc.
 *
 * @author Jay
 */
public abstract class GameData extends ContextPart {

	private GameLoader loader;
	private boolean paused = false;

	public final void setComponents(GameLoader loader) {
		this.loader = loader;
	}

	protected final GameLoader loader() {
		return loader;
	}

	public boolean paused() {
		return paused;
	}

	public void togglePause() {
		paused = !paused;
	}

}
