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
	 * {@link GameContext#init(java.util.Queue) GameContext#init()}.
	 */
	protected void doInit() {
	}

}
