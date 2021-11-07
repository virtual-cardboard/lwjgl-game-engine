package context.visuals.gui.traits;

import common.math.Vector2i;

public interface HasClickEffect extends HasPressEffect, HasReleaseEffect {

	@Override
	default void doReleaseEffect(Vector2i cursorCoordinates) {
		if (isPressed()) {
			HasReleaseEffect.super.doReleaseEffect(cursorCoordinates);
		}
	}

}
