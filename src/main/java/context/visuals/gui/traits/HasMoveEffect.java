package context.visuals.gui.traits;

import java.util.function.Supplier;

import engine.common.event.GameEvent;

public interface HasMoveEffect {

	public Supplier<GameEvent> getMoveEffect();

	public default GameEvent doMoveEffect() {
		Supplier<GameEvent> moveEffect = getMoveEffect();
		return moveEffect != null ? moveEffect.get() : null;
	}

}
