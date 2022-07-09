package context.input.event;

import org.lwjgl.glfw.GLFW;

public final class KeyPressedInputEvent extends GameInputEvent {

	private final int code;

	public KeyPressedInputEvent(int code) {
		this.code = code;
	}

	/**
	 * Getter for the key code of the key pressed.
	 * <p>
	 * When comparing the key code to a <code>char</code>, always compare it with an uppercase character.
	 * E.g. 'W' instead of 'w'.
	 * <p>
	 * When checking for a non-letter character, consider comparing the key code with a GLFW constant.
	 * E.g. {@link GLFW#GLFW_KEY_LEFT_CONTROL}.
	 *
	 * @return the key code
	 */
	public int code() {
		return code;
	}

}
