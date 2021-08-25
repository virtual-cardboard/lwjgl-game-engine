package context;

import java.util.Queue;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import common.loader.Loader;
import common.timestep.WindowFrameUpdateTimer;
import context.input.event.GameInputEvent;
import context.logic.TimeAccumulator;

/**
 * A container for a game context to make switching game contexts thread-safe.
 * 
 * Without a wrapper, swapping contexts requires updating every reference to the
 * context. Aside from being cumbersome, synchronization errors can occur
 * because swapping is not instantaneous.
 * 
 * With the context wrapper, swapping contexts becomes thread-safe. Every
 * reference to the context is replaced with a reference to the wrapper, so
 * swapping contexts is instantaneous with one reference update.
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
	private final WindowFrameUpdateTimer windowFrameUpdateTimer;
	private final Loader loader;

	/**
	 * A constructor that takes in the context, input buffer, logic timer,
	 * windowFrameUpdateTimer, and loader.
	 * 
	 * @param context                the starting {@link GameContext}
	 * @param inputBuffer            {@link Queue} of {@link GameInputEvent}s
	 * @param accumulator            the logic timer
	 * @param windowFrameUpdateTimer the {@link WindowFrameUpdateTimer}
	 * @param loader                 the {@link Loader}
	 */
	public GameContextWrapper(GameContext context, Queue<GameInputEvent> inputBuffer, TimeAccumulator accumulator,
			WindowFrameUpdateTimer windowFrameUpdateTimer, Loader loader) {
		this.context = context;
		context.setWrapper(this);
		this.inputBuffer = inputBuffer;
		this.accumulator = accumulator;
		this.windowFrameUpdateTimer = windowFrameUpdateTimer;
		windowFrameUpdateTimer.setWrapper(this);
		this.loader = loader;
	}

	/**
	 * Swaps current context for the provided context. Puts a write lock on the
	 * reference.
	 * 
	 * @param context the context to transition to
	 */
	public void transition(GameContext context) {
		synchronized (contextLock.writeLock()) {
			context.setWrapper(this);
			context.init(inputBuffer);
			this.context = context;
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

	public WindowFrameUpdateTimer getWindowFrameUpdateTimer() {
		return windowFrameUpdateTimer;
	}

	public Loader getLoader() {
		return loader;
	}

}
