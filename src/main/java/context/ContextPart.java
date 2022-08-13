package context;

import java.util.Queue;

import engine.common.loader.GameLoader;
import engine.common.timestep.GameLogicTimer;

public abstract class ContextPart {

	private GameContext context;

	public GameContext context() {
		return context;
	}

	public void setContext(GameContext context) {
		this.context = context;
	}

	/**
	 * Initializes the context part. This is called by
	 * {@link GameContext#init(Queue, Queue, GameLogicTimer, GameLoader)}.
	 */
	protected void init() {
	}

	/**
	 * Clean up actions if the game is stopped suddenly. Called by
	 * {@link GameContext#terminate() terminate}.
	 */
	protected void terminate() {
	}

	public final ResourcePack resourcePack() {
		return context.resourcePack();
	}

}
