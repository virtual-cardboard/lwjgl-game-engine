package context.input;

import java.util.function.Function;
import java.util.function.Predicate;

import context.input.event.GameInputEvent;
import engine.common.event.GameEvent;

/**
 * <p>
 * Handles {@link GameInputEvent GameInputEvents}. When {@link GameInput}
 * receives a <code>GameInputEvent</code>, {@link GameInput#handleAll()} is
 * called. It will loop through the <code>GameInputHandler</code>s for that type
 * of event. For each handler, <code>GameInput</code> checks if the handler is
 * satisfied. If the predicate is met, the function is run, and then the loop
 * breaks if <code>consumes</code> is true.
 * </p>
 * <p>
 * The reason <code>GameInput</code> does not use {@link Function}s is that we
 * can have a <code>consumes</code> field in <code>GameInputEventHandler</code>.
 * </p>
 * 
 * @author Jay
 *
 * @param <T> the type of GameInputEvent to handle
 */
class GameInputEventHandler<T extends GameInputEvent> implements Function<T, GameEvent> {

	/**
	 * The predicate that determines if the function will be run.
	 */
	private final Predicate<T> predicate;
	/**
	 * The function that is run on the {@link GameInputEvent}.
	 */
	private final Function<T, GameEvent> function;
	/**
	 * If the event handler consumes the {@link GameInputEvent}.
	 */
	private final boolean consumes;

	/**
	 * Creates a {@link GameInputEventHandler} with the given <code>predicate</code>
	 * and <code>function</code>, and if the event handler consumes.
	 * 
	 * @param predicate the predicate to check if a {@link GameInputEvent} should be
	 *                  handled
	 * @param function  the function that is applied to a handled
	 *                  <code>GameInputEvent</code>
	 * @param consumes  whether or not this event handler should consume a handled
	 *                  <code>GameInputEvent</code>
	 */
	public GameInputEventHandler(Predicate<T> predicate, Function<T, GameEvent> function, boolean consumes) {
		this.predicate = predicate;
		this.function = function;
		this.consumes = consumes;
	}

	/**
	 * Creates a {@link GameInputEventHandler} with the given <code>function</code>
	 * and a <code>predicate</code> that always returns <code>true</code> that does
	 * not consume handled <code>GameInputEvent</code>s.
	 * 
	 * @param function the function that is applied to a handled
	 *                 <code>GameInputEvent</code>
	 */
	public GameInputEventHandler(Function<T, GameEvent> function) {
		this(GameInputEventHandler::returnTrue, function, false);
	}

	/**
	 * 
	 * @param gameInputEvent the {@link GameInputEvent} to test
	 * @return if <code>gameInputEvent</code> passes the
	 *         {@link GameInputEventHandler#predicate predicate}.
	 */
	public boolean isSatisfiedBy(T gameInputEvent) {
		return predicate == null || predicate.test(gameInputEvent);
	}

	/**
	 * Applies the {@link GameInputEventHandler#function function} to
	 * <code>gameInputEvent</code>.
	 * 
	 * @param gameInputEvent the <code>GameInputEvent</code> to apply
	 * 
	 * @see GameInputEvent
	 */
	@Override
	public GameEvent apply(T gameInputEvent) {
		return function.apply(gameInputEvent);
	}

	/**
	 * @return If this event handler {@link GameInputEventHandler#consumes
	 *         consumes}.
	 */
	public boolean doesConsume() {
		return consumes;
	}

	private static final boolean returnTrue(GameInputEvent gameInputEvent) {
		return true;
	}

}
