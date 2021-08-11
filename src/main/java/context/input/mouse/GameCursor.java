package context.input.mouse;

import common.coordinates.IntCoordinates;

public class GameCursor {

	private IntCoordinates cursorCoordinates;
	private boolean[] pressedButtons;

	public GameCursor() {
		cursorCoordinates = new IntCoordinates(0, 0);
		pressedButtons = new boolean[8];
	}

	public IntCoordinates getCursorCoordinates() {
		return cursorCoordinates;
	}

	public void setCursorCoordinates(IntCoordinates cursorCoordinates) {
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
