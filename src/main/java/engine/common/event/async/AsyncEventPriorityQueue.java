package engine.common.event.async;

import static java.util.Comparator.comparingLong;

import java.util.PriorityQueue;

public class AsyncEventPriorityQueue extends PriorityQueue<AsyncGameEvent> {

	private static final long serialVersionUID = 3913311744899406460L;

	public AsyncEventPriorityQueue() {
		super(comparingLong(asyncEvent -> asyncEvent.scheduledTick()));
	}

	public AsyncGameEvent peek(long tick) {
		AsyncGameEvent peek = super.peek();
		if (peek == null) {
			return null;
		}
		if (peek.shouldHandle(tick)) {
			return peek;
		}
		return null;
	}

	public AsyncGameEvent peekTimeSensitive(long tick) {
		AsyncGameEvent peek = super.peek();
		if (peek == null) {
			return null;
		}
		if (peek.shouldHandleThisTick(tick)) {
			return peek;
		}
		return null;
	}

}
