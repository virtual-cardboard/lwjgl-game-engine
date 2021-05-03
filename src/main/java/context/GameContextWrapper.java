package context;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import context.input.GameInputBuffer;
import context.logic.GameLogicTimer;
import context.visuals.renderer.GameRenderer;

/**
 * A container for a game context to make switching game contexts thread-safe.
 * 
 * Without a wrapper, swapping contexts requires updating every reference to the
 * context. Aside from being cumbersome, synchronization errors can occur
 * because swapping is not instantaneous. With the context wrapper, swapping
 * contexts becomes thread-safe. Every reference to the context is replaced with
 * a reference to the wrapper, so swapping contexts is instantaneous with one
 * reference update.
 * 
 * @author Jay, Lunkle
 *
 */
public class GameContextWrapper {

	/**
	 * Context that is being wrapped.
	 */
	private GameContext context;

	/**
	 * This read-write lock is not a lock on the context itself. The read and write
	 * lock is on the accessibility of the context reference.
	 */
	private final ReadWriteLock contextLock = new ReentrantReadWriteLock();

	private final GameRenderer renderer;
	private final GameInputBuffer inputBuffer;
	private final GameLogicTimer logicTimer;

	/**
	 * A constructor that takes in the renderer, input buffer, and logic timer.
	 * 
	 * @param renderer
	 * @param inputBuffer
	 * @param logicTimer
	 */
	public GameContextWrapper(GameRenderer renderer, GameInputBuffer inputBuffer, GameLogicTimer logicTimer) {
		this.renderer = renderer;
		this.inputBuffer = inputBuffer;
		this.logicTimer = logicTimer;
	}

	/**
	 * Swaps current context for the provided context.
	 * 
	 * @param context
	 */
	public void transition(GameContext context) {
		synchronized (contextLock.writeLock()) {
			this.context = context;
			context.wrapper = this;
		}
	}

	/**
	 * The read lock is on the context reference, not the context itself. The
	 * context is still mutable and the caller is allowed to mutate the context. The
	 * read lock is to prevent
	 * 
	 * @return game context
	 */
	public GameContext getContext() {
		synchronized (contextLock.readLock()) {
			return context;
		}
	}

	public GameRenderer getRenderer() {
		return renderer;
	}

	public GameInputBuffer getInputBuffer() {
		return inputBuffer;
	}

	public GameLogicTimer getLogicTimer() {
		return logicTimer;
	}

}
