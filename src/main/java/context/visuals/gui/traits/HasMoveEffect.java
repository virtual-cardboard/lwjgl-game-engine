package context.visuals.gui.traits;

import java.util.function.Consumer;

import common.coordinates.IntCoordinates;

public interface HasMoveEffect {

	public Consumer<IntCoordinates> getMoveEffect();

	public default void doMoveEffect(IntCoordinates cursorCoordinates) {
		getMoveEffect().accept(cursorCoordinates);
	}

}
