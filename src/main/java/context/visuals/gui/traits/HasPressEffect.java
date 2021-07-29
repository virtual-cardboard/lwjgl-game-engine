package context.visuals.gui.traits;

import java.util.function.Consumer;

import common.coordinates.IntCoordinates;

public interface HasPressEffect {

	public boolean isPressed();

	public void setPressed(boolean pressed);

	public Consumer<IntCoordinates> getPressEffect();

	public default void doPressEffect(IntCoordinates cursorCoordinates) {
		getPressEffect().accept(cursorCoordinates);
		setPressed(true);
	}

}
