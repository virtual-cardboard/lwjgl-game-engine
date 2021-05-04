package context.input.event;

import common.coordinates.VectorCoordinates;
import common.source.GameSource;

public class MouseMovedInputEvent extends AbstractGameInputEvent {

	private int mouseX;
	private int mouseY;

	public MouseMovedInputEvent(long time, GameSource source, int mouseX, int mouseY) {
		super(time, source);
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
