package context.visuals.gui.effects;

import context.input.mouse.GameCursor;
import engine.common.event.GameEvent;

/**
 * A gui with a release effect.
 */
public interface HasReleaseEffect {

	/** Whether the gui is currently pressed. */
	boolean pressed();

	/**
	 * The function that gets called when the mouse is pressed or released.
	 * <p>
	 * Implementations should store a <code>boolean pressed</code> field and implement the {@link #pressed()} method and
	 * this method as standard getters and setters.
	 *
	 * @param pressed Whether the gui is currently pressed.
	 */
	void setPressed(boolean pressed);

	GameEvent doReleaseEffect(GameCursor cursor);

}
