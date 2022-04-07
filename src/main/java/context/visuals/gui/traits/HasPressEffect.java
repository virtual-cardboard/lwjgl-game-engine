package context.visuals.gui.traits;

import java.util.function.Supplier;

import common.event.GameEvent;

public interface HasPressEffect {

	public boolean isPressed();

	public void setPressed(boolean pressed);

	public Supplier<GameEvent> getPressEffect();

	public default GameEvent doPressEffect() {
		setPressed(true);
		Supplier<GameEvent> pressEffect = getPressEffect();
		return pressEffect != null ? pressEffect.get() : null;
	}

}
