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
import context.input.event.KeyRepeatedInputEvent;

/**
 * Decorates a key pressed event into an {@link KeyPressedInputEvent} and adds
 * it into the input buffer. <br>
 * Decorates a key released event into an {@link KeyReleasedInputEvent} and adds
 * it into the input buffer. <br>
 * Decorates a key repeated event into an {@link KeyRepeatedInputEvent} and adds
 * it into the input buffer.
 * 
 * @author Lunkle
 *
 */
public class KeyCallback extends GLFWKeyCallback {

	private final Queue<GameInputEvent> inputEventBuffer;

	public KeyCallback(Queue<GameInputEvent> inputEventBuffer) {
		this.inputEventBuffer = inputEventBuffer;
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
					inputEventBuffer.add(new KeyPressedInputEvent(key));
					break;
				case GLFW_RELEASE:
					inputEventBuffer.add(new KeyReleasedInputEvent(key));
					break;
				case GLFW_REPEAT:
					inputEventBuffer.add(new KeyRepeatedInputEvent(key));
					break;
				default:
					break;
			}
		}
	}

}
