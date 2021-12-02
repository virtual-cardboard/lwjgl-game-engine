package context.visuals.lwjgl;

public abstract class GLObject {

	private boolean initialized;

	protected final void verifyInitialized() {
		if (!initialized) {
			throw new IllegalStateException("GLObject not initialized.");
		}
	}

	protected final void confirmInitialization() {
		if (initialized) {
			throw new IllegalStateException("GLObject already initialized.");
		}
		initialized = true;
	}

	public boolean initialized() {
		return initialized;
	}

}
