package context.input.event;

import java.awt.event.KeyEvent;

import common.source.GameSource;
import context.data.user.LocalUser;

public final class KeyPressedInputEvent extends GameInputEvent {

	private static final long serialVersionUID = -9019957455388041026L;

	private final int code;

	public KeyPressedInputEvent(long time, GameSource source, int code) {
		super(time, source);
		this.code = code;
	}

	public KeyPressedInputEvent(int code) {
		super(LocalUser.LOCAL_USER);
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
