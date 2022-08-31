package context.visuals.gui.effects;

import context.input.mouse.GameCursor;
import engine.common.event.GameEvent;

/**
 * A gui with an effect when the cursor moves on it.
 * <p>
 * This effect can be combined with {@link HasReleaseEffect} and/or {@link HasPressEffect} to make a drag effect.
 */
public interface HasMoveEffect {

	GameEvent doMoveEffect(GameCursor cursor);

}
