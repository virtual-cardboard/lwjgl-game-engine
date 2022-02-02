package context.audio.lwjgl;

public abstract class ALObject {

	private boolean initialized;

	protected final void verifyInitialized() {
		if (!initialized) {
			throw new IllegalStateException("GLObject not initialized.");
		}
	}

	protected final void initialize() {
		if (initialized) {
			throw new IllegalStateException("GLObject already initialized.");
		}
		initialized = true;
	}

	public boolean initialized() {
		return initialized;
	}

}
