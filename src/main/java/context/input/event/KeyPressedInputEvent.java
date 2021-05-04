package context.input.event;

import java.awt.event.KeyEvent;

import common.source.GameSource;

public class KeyPressedInputEvent extends AbstractGameInputEvent {

	private int keyCode;

	public KeyPressedInputEvent(long time, GameSource source, int keyCode) {
		super(time, source);
		this.keyCode = keyCode;
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
	public int getKeyCode() {
		return keyCode;
	}

}
