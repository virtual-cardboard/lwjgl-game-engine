package context;

public abstract class ContextPart {

	private GameContext context;

	public GameContext getBundle() {
		return context;
	}

	public void setBundle(GameContext bundle) {
		this.context = bundle;
	}

}
