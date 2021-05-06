package context;

public abstract class ContextPart {

	private GameContext context;

	public GameContext getContext() {
		return context;
	}

	public void setContext(GameContext context) {
		this.context = context;
	}

	protected void doInit() {
	}

}
