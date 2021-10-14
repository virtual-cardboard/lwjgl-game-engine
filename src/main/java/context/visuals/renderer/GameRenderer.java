package context.visuals.renderer;

import context.GameContext;
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
	 * {@link GameContext}
	 * 
	 * @param context the {@link GameContext}
	 */
	public GameRenderer(GameContext context) {
		this.resourcePack = context != null ? context.wrapper().resourcePack() : null;
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
