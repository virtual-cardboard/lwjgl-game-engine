package context;

import java.util.Queue;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import context.input.event.GameInputEvent;
import context.logic.TimeAccumulator;

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

	private final Queue<GameInputEvent> inputBuffer;
	private final TimeAccumulator accumulator;

	/**
	 * A constructor that takes in the renderer, input buffer, and logic timer.
	 * 
	 * @param inputBuffer
	 * @param logicTimer
	 */
	public GameContextWrapper(Queue<GameInputEvent> inputBuffer, TimeAccumulator accumulator, GameContext context) {
		this.inputBuffer = inputBuffer;
		this.accumulator = accumulator;
		this.context = context;
	}

	/**
	 * Swaps current context for the provided context. Puts a write lock on the
	 * reference.
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

	public Queue<GameInputEvent> getInputBuffer() {
		return inputBuffer;
	}

	public TimeAccumulator getAccumulator() {
		return accumulator;
	}

}
