package context.input.mouse;

import common.coordinates.IntCoordinates;

public class GameCursor {

	private IntCoordinates cursorCoordinates;

	public GameCursor() {
		cursorCoordinates = new IntCoordinates(0, 0);
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

}
