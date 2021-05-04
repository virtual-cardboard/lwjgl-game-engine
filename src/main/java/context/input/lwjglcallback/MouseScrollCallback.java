package context.input.lwjglcallback;

import java.util.Queue;

import org.lwjgl.glfw.GLFWScrollCallback;

import context.input.event.GameInputEvent;
import context.input.event.MouseScrolledInputEvent;

/**
 * Decorates a mouse scrolled event into an {@link MouseScrolledInputEvent} and
 * adds it into the input buffer.
 * 
 * @author Lunkle
 *
 */
public class MouseScrollCallback extends GLFWScrollCallback {

	private final Queue<GameInputEvent> inputBuffer;

	public MouseScrollCallback(Queue<GameInputEvent> inputBuffer) {
		this.inputBuffer = inputBuffer;
	}

	/**
	 * This is the function that is called internally by GLFW called when the mouse
	 * wheel is scrolled.
	 */
	@Override
	public void invoke(long window, double xOffset, double yOffset) {
		inputBuffer.add(new MouseScrolledInputEvent((float) xOffset, (float) yOffset));
	}

}
