package context.input.event;

import common.coordinates.VectorCoordinates;
import state.source.GameSource;
import state.user.LocalUser;

public final class MouseMovedInputEvent extends GameInputEvent {

	private final int mouseX;
	private final int mouseY;

	public MouseMovedInputEvent(long time, GameSource source, int mouseX, int mouseY) {
		super(time, source);
		this.mouseX = mouseX;
		this.mouseY = mouseY;
	}

	public MouseMovedInputEvent(int mouseX, int mouseY) {
		super(LocalUser.LOCAL_USER);
		this.mouseX = mouseX;
		this.mouseY = mouseY;
	}

	public int getMouseX() {
		return mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}

	public VectorCoordinates getMouseCoords() {
		return new VectorCoordinates(mouseX, mouseY);
	}

}
