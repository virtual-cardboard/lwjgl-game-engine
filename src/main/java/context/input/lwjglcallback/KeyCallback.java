package context.input.lwjglcallback;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_UNKNOWN;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_REPEAT;

import java.util.Queue;

import org.lwjgl.glfw.GLFWKeyCallback;

import context.input.event.GameInputEvent;
import context.input.event.KeyPressedInputEvent;
import context.input.event.KeyReleasedInputEvent;
import context.input.event.KeyRepeatInputEvent;

/**
 * Decorates a key pressed event into an {@link KeyPressedInputEvent} and adds
 * it into the input buffer. <br>
 * Decorates a key released event into an {@link KeyReleasedInputEvent} and adds
 * it into the input buffer. <br>
 * Decorates a key repeated event into an {@link KeyRepeatInputEvent} and adds
 * it into the input buffer.
 * 
 * @author Lunkle
 *
 */
public class KeyCallback extends GLFWKeyCallback {

	private final Queue<GameInputEvent> inputBuffer;

	public KeyCallback(Queue<GameInputEvent> inputBuffer) {
		this.inputBuffer = inputBuffer;
	}

	/**
	 * This is the function that is called internally by GLFW called when a key is
	 * pressed, released, or repeated.
	 */
	@Override
	public final void invoke(long window, int key, int scancode, int action, int mods) {
		if (key != GLFW_KEY_UNKNOWN) {
			switch (action) {
				case GLFW_PRESS:
					inputBuffer.add(new KeyPressedInputEvent(key));
					break;
				case GLFW_RELEASE:
					inputBuffer.add(new KeyReleasedInputEvent(key));
					break;
				case GLFW_REPEAT:
					inputBuffer.add(new KeyRepeatInputEvent(key));
					break;
				default:
					break;
			}
		}
	}

}
