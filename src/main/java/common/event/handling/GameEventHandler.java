package common.event.handling;

import java.util.function.Consumer;
import java.util.function.Predicate;

import common.event.GameEvent;

/**
 * Handles {@link GameEvent GameEvents}. Given a game event, the
 * {@link Predicate predicate}, {@link Consumer consumer}, and {@link Boolean
 * boolean} are intended to work together as follows: <br>
 * 1. Check if the given event satisfies the predicate.<br>
 * 2. If the event does not satisfy the predicate, end.<br>
 * 3. Otherwise, run the consumer on the event.<br>
 * 4. Boolean indicated whether or not subsequent handlers are able to view the
 * event.
 * 
 * @author Lunkle
 *
 * @param <T> the type of GameEvent to handle
 */
public class GameEventHandler<T extends GameEvent> implements Consumer<T> {

	/**
	 * The predicate that determines if the consumer will be run.
	 */
	private final Predicate<T> predicate;
	/**
	 * The consumer that is run on the {@link GameEvent}.
	 */
	private final Consumer<T> consumer;
	/**
	 * Whether the event handler consumes the {@link GameEvent}.
	 */
	private final boolean consumes;

	/**
	 * Creates a {@link GameEventHandler} with the given <code>predicate</code> and
	 * <code>consumer</code>, and whether the event handler consumes.
	 * 
	 * @param predicate the predicate to check if a {@link GameEvent} should be
	 *                  handled
	 * @param consumer  the consumer that is applied to a handled
	 *                  <code>GameEvent</code>
	 * @param consumes  whether or not this event handler should consume a handled
	 *                  <code>GameEvent</code>
	 */
	public GameEventHandler(Predicate<T> predicate, Consumer<T> consumer, boolean consumes) {
		this.predicate = predicate;
		this.consumer = consumer;
		this.consumes = consumes;
	}

	/**
	 * Creates a {@link GameEventHandler} with the given <code>consumer</code>, and
	 * whether the event handler consumes.
	 * 
	 * @param consumer the consumer that is applied to a handled
	 *                 <code>GameEvent</code>
	 * @param consumes whether or not this event handler should consume a handled
	 *                 <code>GameEvent</code>
	 */
	public GameEventHandler(Consumer<T> consumer, boolean consumes) {
		this(GameEventHandler::returnTrue, consumer, consumes);
	}

	/**
	 * Creates a {@link GameEventHandler} with the given <code>consumer</code> and a
	 * <code>predicate</code> that always returns <code>true</code> that does not
	 * consume handled <code>GameEvent</code>s.
	 * 
	 * @param consumer the function that is applied to a <code>GameEvent</code>
	 */
	public GameEventHandler(Consumer<T> consumer) {
		this(GameEventHandler::returnTrue, consumer, false);
	}

	/**
	 * 
	 * @param gameEvent the {@link GameEvent} to test
	 * @return if <code>gameEvent</code> passes the
	 *         {@link GameEventHandler#predicate predicate}.
	 */
	public boolean isSatisfiedBy(T gameEvent) {
		return predicate == null || predicate.test(gameEvent);
	}

	/**
	 * Applies the {@link GameEventHandler#consumer function} to
	 * <code>gameEvent</code>.
	 * 
	 * @param gameEvent the <code>GameEvent</code> to apply the consumer on
	 * 
	 * @see GameEvent
	 */
	@Override
	public void accept(T gameEvent) {
		consumer.accept(gameEvent);
	}

	/**
	 * @return If this event handler {@link GameEventHandler#consumes consumes}.
	 */
	public boolean doesConsume() {
		return consumes;
	}

	private static final boolean returnTrue(GameEvent gameEvent) {
		return true;
	}

}
