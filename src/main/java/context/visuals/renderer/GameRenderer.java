package context.visuals.renderer;

import context.GameContextWrapper;
import context.ResourcePack;
import context.visuals.GameVisuals;

/**
 * Renders objects. Crate implementation(s) of this, and use them in
 * {@link GameVisuals}'s {@link GameVisuals#render() render()} function.
 * 
 * @author Jay
 *
 */
public abstract class GameRenderer {

	/**
	 * The resource pack.
	 */
	private ResourcePack resourcePack;

	/**
	 * Creates a {@link GameRenderer} using the {@link ResourcePack} from a
	 * {@link GameContextWrapper}
	 * 
	 * @param wrapper the {@link GameContextWrapper}
	 */
	public GameRenderer(GameContextWrapper wrapper) {
		this.resourcePack = wrapper != null ? wrapper.resourcePack() : null;
	}

	/**
	 * Gets the resource pack
	 * 
	 * @return the {@link ResourcePack}
	 */
	public ResourcePack resourcePack() {
		return resourcePack;
	}

}
