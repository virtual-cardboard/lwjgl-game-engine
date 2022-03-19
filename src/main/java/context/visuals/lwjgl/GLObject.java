package context.visuals.lwjgl;

import context.ResourcePack;

/**
 * 
 * @author Lunkle
 *
 */
public abstract class GLObject {

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

	protected void addTo(ResourcePack resourcePack) {
	}

	public void putInto(String name, ResourcePack resourcePack) {
		throw new RuntimeException("Tried to add " + name + " of type " + this.getClass().getName()
				+ " into ResourcePack but the GLObject.putInto(String name, ResourcePack resourcePack)"
				+ " method was not overrided. Override the method to insert the GLObject into the"
				+ " resource pack.");
	}

}
