package context;

import common.entity.User;
import context.input.GameInputBuffer;
import context.logic.GameLogicTimer;
import context.visuals.renderer.GameRenderer;

/**
 * A container for a game bundle to make switching bundles easier.
 * 
 * Bundle wrappers make swapping bundles easier, because without a wrapper,
 * everything would have to have a reference to the old bundle, making it so
 * that if you wanted to swap the bundle, you'd have to individually make the
 * swap at every instance of its references. Aside from being cumbersome,
 * synchronization errors can occur because swapping is not instantaneous.
 * 
 * Wrappers allow instantaneous swappping to be achieved because everyone has a
 * reference to the same bundle wrapper.
 * 
 * @author Jay
 *
 */
public class GameBundleWrapper {

	private GameBundle bundle;
	private GameRenderer renderer;
	private GameInputBuffer inputBuffer;
	private GameLogicTimer logicTimer;
	private User user;

	/**
	 * A constructor that takes in the initial bundle. Should not be null.
	 * 
	 * @param bundle not null
	 */
	public GameBundleWrapper(GameBundle bundle) {
		this.bundle = bundle;
		bundle.wrapper = this;
		inputBuffer = new GameInputBuffer();
	}

	/**
	 * Swaps current bundle for the provided bundle. Should not be null.
	 * 
	 * @param bundle not null
	 */
	public void transition(GameBundle bundle) {
		this.bundle = bundle;
		bundle.wrapper = this;
	}

	public GameBundle getBundle() {
		return bundle;
	}

	public GameRenderer getRenderer() {
		return renderer;
	}

	public void setRenderer(GameRenderer renderer) {
		this.renderer = renderer;
	}

	public GameInputBuffer getInputBuffer() {
		return inputBuffer;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public GameLogicTimer getLogicTimer() {
		return logicTimer;
	}

	public void setLogicTimer(GameLogicTimer logicTimer) {
		this.logicTimer = logicTimer;
	}

}
