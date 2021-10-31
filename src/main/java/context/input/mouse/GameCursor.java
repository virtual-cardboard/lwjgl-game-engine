package context.input.mouse;

import common.math.Vector2f;

public class GameCursor {

	private Vector2f cursorCoordinates;
	private boolean[] pressedButtons;

	public GameCursor() {
		cursorCoordinates = new Vector2f();
		pressedButtons = new boolean[8];
	}

	public Vector2f cursorCoordinates() {
		return cursorCoordinates;
	}

	public void setCursorCoordinates(Vector2f cursorCoordinates) {
		this.cursorCoordinates.set(cursorCoordinates);
	}

	public void setCursorCoordinates(int x, int y) {
		this.cursorCoordinates.set(x, y);
	}

	public boolean leftButtonPressed() {
		return pressedButtons[0];
	}

	public boolean rightButtonPressed() {
		return pressedButtons[1];
	}

	public boolean middleButtonPressed() {
		return pressedButtons[2];
	}

	public boolean[] getPressedButtons() {
		return pressedButtons;
	}
}
