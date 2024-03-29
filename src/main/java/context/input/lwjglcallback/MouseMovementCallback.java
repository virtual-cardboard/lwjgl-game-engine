package context.input.lwjglcallback;

import java.util.Queue;

import org.lwjgl.glfw.GLFWCursorPosCallback;

import context.input.event.GameInputEvent;
import context.input.event.MouseMovedInputEvent;

/**
 * Decorates a mouse moved event or mouse dragged event into an
 * {@link MouseMovedInputEvent} and adds it into the input buffer.
 * 
 * @author Lunkle
 *
 */
public class MouseMovementCallback extends GLFWCursorPosCallback {

	private final Queue<GameInputEvent> inputEventBuffer;

	public MouseMovementCallback(Queue<GameInputEvent> inputEventBuffer) {
		this.inputEventBuffer = inputEventBuffer;
	}

	/**
	 * This is the function that is called internally by GLFW called when the mouse
	 * is moved.
	 */
	@Override
	public void invoke(long window, double xPos, double yPos) {
		inputEventBuffer.add(new MouseMovedInputEvent((int) xPos, (int) yPos));
	}

}
