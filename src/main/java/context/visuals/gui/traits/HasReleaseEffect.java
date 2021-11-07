package context.visuals.gui.traits;

import java.util.function.Consumer;

import common.math.Vector2i;

public interface HasReleaseEffect {

	public boolean isPressed();

	public void setPressed(boolean pressed);

	public Consumer<Vector2i> getReleaseEffect();

	public default void doReleaseEffect(Vector2i cursorCoordinates) {
		getReleaseEffect().accept(cursorCoordinates);
		setPressed(false);
	}

}
