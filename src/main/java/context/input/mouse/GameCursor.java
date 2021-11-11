package context.input.mouse;

import common.math.Vector2f;

public class GameCursor {

	private Vector2f previousPos = new Vector2f();
	private Vector2f pos = new Vector2f();
	private boolean[] pressedButtons = new boolean[8];

	public Vector2f previousPos() {
		return previousPos;
	}

	public Vector2f pos() {
		return pos;
	}

	public Vector2f velocity() {
		return pos.copy().sub(previousPos);
	}

	public void setPos(Vector2f pos) {
		setPos(pos.x, pos.y);
	}

	public void setPos(float x, float y) {
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
