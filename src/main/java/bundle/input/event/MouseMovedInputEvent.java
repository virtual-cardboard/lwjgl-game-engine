package bundle.input.event;

import common.coordinates.Vector2f;
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

	public Vector2f getMouseCoords() {
		return new Vector2f(mouseX, mouseY);
	}

	@Override
	public String getName() {
		return this.getName();
	}

}
