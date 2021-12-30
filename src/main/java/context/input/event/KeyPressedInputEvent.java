package context.input.event;

import java.awt.event.KeyEvent;

public final class KeyPressedInputEvent extends GameInputEvent {

	private final int code;

	public KeyPressedInputEvent(int code) {
		this.code = code;
	}

	/**
	 * Getter for the key code of the key pressed. To use this, have an if/else
	 * statement and check if the key code is equal to a vk key code. Example:
	 * <p>
	 * <code>
	 * if (getKeyCode == {@link KeyEvent}.VK_W) {<p>
	 *     character.moveUp();<p>
	 * }
	 * </code>
	 * 
	 * @return the key code
	 * 
	 */
	public int code() {
		return code;
	}

}
