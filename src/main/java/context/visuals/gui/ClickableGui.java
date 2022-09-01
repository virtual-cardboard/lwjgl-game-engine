package context.visuals.gui;

import context.input.mouse.GameCursor;
import context.visuals.gui.effects.HasPressEffect;
import context.visuals.gui.effects.HasReleaseEffect;
import engine.common.event.GameEvent;

/**
 * A gui that can be clicked.
 * <p>
 * A "click" is a press on the gui followed by a release on the same gui.
 */
public abstract class ClickableGui extends Gui implements HasPressEffect, HasReleaseEffect {

	/** Whether the gui is currently pressed. */
	private boolean pressed = false;

	/**
	 * The function that gets called when a click is registered.
	 *
	 * @return The event that should be sent from the GameInput to the GameLogic.
	 */
	public abstract GameEvent doClickEffect();

	@Override
	public GameEvent doPressEffect(GameCursor cursor) {
		return null;
	}

	@Override
	public GameEvent doReleaseEffect(GameCursor cursor) {
		if (pressed) {
			return doClickEffect();
		}
		return null;
	}

	@Override
	public boolean pressed() {
		return pressed;
	}

	@Override
	public void setPressed(boolean pressed) {
		this.pressed = pressed;
	}

}
