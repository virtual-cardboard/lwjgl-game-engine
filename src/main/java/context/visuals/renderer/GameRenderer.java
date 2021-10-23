package context.visuals.renderer;

import context.GLContext;
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
	protected ResourcePack resourcePack;
	protected GLContext glContext;

	/**
	 * Creates a {@link GameRenderer} using the {@link ResourcePack} and
	 * {@link GLContext} from a {@link GameContext}
	 * 
	 * @param context   the {@link GameContext}
	 * @param glContext the {@link GLContext}
	 */
	public GameRenderer(GameContext context) {
		this.resourcePack = context != null ? context.resourcePack() : null;
		this.glContext = context != null ? context.glContext() : null;
	}

}
