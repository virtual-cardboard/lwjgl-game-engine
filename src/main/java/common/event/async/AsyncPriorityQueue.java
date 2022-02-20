package common.event.async;

import static java.util.Comparator.comparingLong;

import java.util.PriorityQueue;

public class AsyncPriorityQueue extends PriorityQueue<AsyncGameEvent> {

	private static final long serialVersionUID = 3913311744899406460L;

	public AsyncPriorityQueue() {
		super(comparingLong(asyncEvent -> asyncEvent.scheduledTick()));
	}

}
