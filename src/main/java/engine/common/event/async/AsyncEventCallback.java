package engine.common.event.async;

@FunctionalInterface
public interface AsyncEventCallback {

	void run(AsyncGameEvent event, AsyncEventPriorityQueue queue);

}
