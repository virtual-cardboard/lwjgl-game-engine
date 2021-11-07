package context.visuals.gui.traits;

import java.util.function.Consumer;

import common.math.Vector2i;

public interface HasMoveEffect {

	public Consumer<Vector2i> getMoveEffect();

	public default void doMoveEffect(Vector2i cursorCoordinates) {
		getMoveEffect().accept(cursorCoordinates);
	}

}
