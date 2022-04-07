package context.visuals.gui.traits;

import common.event.GameEvent;

public interface HasClickEffect extends HasPressEffect, HasReleaseEffect {

	@Override
	default GameEvent doReleaseEffect() {
		return isPressed() ? HasReleaseEffect.super.doReleaseEffect() : null;
	}

}
