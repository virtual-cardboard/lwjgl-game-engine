package context.input.mouse;

import common.math.Vector2i;

public class GameCursor {

	private Vector2i previousPos = new Vector2i();
	private Vector2i pos = new Vector2i();
	private boolean[] pressedButtons = new boolean[8];

	public Vector2i previousPos() {
		return previousPos;
	}

	public Vector2i pos() {
		return pos;
	}

	public Vector2i velocity() {
		return pos.copy().sub(previousPos);
	}

	public void setPos(Vector2i pos) {
		setPos(pos.x, pos.y);
	}

	public void setPos(int x, int y) {
		previousPos.set(pos);
		this.pos.set(x, y);
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
