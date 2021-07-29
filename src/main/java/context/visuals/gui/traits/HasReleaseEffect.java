package context.visuals.gui.traits;

import java.util.function.Consumer;

import common.coordinates.IntCoordinates;

public interface HasReleaseEffect {

	public boolean isPressed();

	public void setPressed(boolean pressed);

	public Consumer<IntCoordinates> getReleaseEffect();

	public default void doReleaseEffect(IntCoordinates cursorCoordinates) {
		getReleaseEffect().accept(cursorCoordinates);
		setPressed(false);
	}

}
