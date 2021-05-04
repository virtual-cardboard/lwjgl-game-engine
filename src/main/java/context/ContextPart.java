package context;

public abstract class ContextPart {

	private GameContext context;

	public GameContext getContext() {
		return context;
	}

	public void setContext(GameContext bundle) {
		this.context = bundle;
	}

}
