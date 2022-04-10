package context.visuals.gui.traits;

import java.util.function.Supplier;

import engine.common.event.GameEvent;

public interface HasReleaseEffect {

	public boolean isPressed();

	public void setPressed(boolean pressed);

	public Supplier<GameEvent> getReleaseEffect();

	public default GameEvent doReleaseEffect() {
		setPressed(false);
		Supplier<GameEvent> releaseEffect = getReleaseEffect();
		return releaseEffect != null ? releaseEffect.get() : null;
	}

}
