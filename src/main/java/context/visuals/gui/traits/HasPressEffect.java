package context.visuals.gui.traits;

import java.util.function.Consumer;

import common.math.Vector2i;

public interface HasPressEffect {

	public boolean isPressed();

	public void setPressed(boolean pressed);

	public Consumer<Vector2i> getPressEffect();

	public default void doPressEffect(Vector2i cursorCoordinates) {
		getPressEffect().accept(cursorCoordinates);
		setPressed(true);
	}

}
