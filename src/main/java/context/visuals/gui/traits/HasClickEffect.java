package context.visuals.gui.traits;

import common.coordinates.IntCoordinates;

public interface HasClickEffect extends HasPressEffect, HasReleaseEffect {

	@Override
	default void doReleaseEffect(IntCoordinates cursorCoordinates) {
		if (isPressed()) {
			HasReleaseEffect.super.doReleaseEffect(cursorCoordinates);
		}
	}

}
