package common.loader;

public class LoadTask {

	protected boolean isLoaded = false;
	protected Loadable loadable;

	public LoadTask(Loadable loadable) {
		this.loadable = loadable;
	}

	public boolean isLoaded() {
		return isLoaded;
	}

	public Loadable getResult() {
		if (isLoaded) {
			return loadable;
		}
		return null;
	}

	public boolean isNull() {
		return false;
	}

	public String getLoadingText() {
		return "Loading " + loadable.toString();
	}

}
