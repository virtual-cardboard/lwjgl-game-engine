package context.input.lwjglcallback;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_REPEAT;

import java.util.Queue;

import org.lwjgl.glfw.GLFWMouseButtonCallback;

import context.input.event.GameInputEvent;
import context.input.event.MousePressedInputEvent;
import context.input.event.MouseReleasedInputEvent;

/**
 * Decorates a mouse pressed event into an {@link MousePressedInputEvent} and
 * adds it into the input buffer.<br>
 * Decorates a mouse released event into an {@link MouseReleasedInputEvent} and
 * adds it into the input buffer.
 * 
 * @author Lunkle
 *
 */
public class MouseButtonCallback extends GLFWMouseButtonCallback {

	private final Queue<GameInputEvent> inputBuffer;

	public MouseButtonCallback(Queue<GameInputEvent> inputBuffer) {
		this.inputBuffer = inputBuffer;
	}

	/**
	 * This is the function that is called internally by GLFW called when a key is
	 * pressed, released, or repeated.
	 */
	@Override
	public void invoke(long window, int button, int action, int mods) {
		switch (action) {
			case GLFW_PRESS:
				inputBuffer.add(new MousePressedInputEvent(button));
				break;
			case GLFW_RELEASE:
				inputBuffer.add(new MouseReleasedInputEvent(button));
				break;
			case GLFW_REPEAT:
			default:
				break;
		}
	}

}
