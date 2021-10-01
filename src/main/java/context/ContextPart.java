package context;

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
	 * {@link GameContext#init(Queue, Queue) init}.
	 */
	protected void init() {
	}

	protected void terminate() {
	}

}
