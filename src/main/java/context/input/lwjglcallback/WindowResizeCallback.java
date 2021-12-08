package context.input.lwjglcallback;

import static org.lwjgl.opengl.GL11.glViewport;

import java.util.Queue;

import org.lwjgl.glfw.GLFWFramebufferSizeCallback;

import context.input.event.FrameResizedInputEvent;
import context.input.event.GameInputEvent;

/**
 * 
 * Decorates a window resize event into an {@link FrameResizedInputEvent} and
 * adds it into the input buffer.
 * 
 * @author Lunkle
 *
 */
public class WindowResizeCallback extends GLFWFramebufferSizeCallback {

	private final Queue<GameInputEvent> inputEventBuffer;

	public WindowResizeCallback(Queue<GameInputEvent> inputEventBuffer) {
		this.inputEventBuffer = inputEventBuffer;
	}

	/**
	 * This is the function that is called internally by GLFW called when the window
	 * is resized.
	 */
	@Override
	public void invoke(long windowId, int width, int height) {
		glViewport(0, 0, width, height);
		inputEventBuffer.add(new FrameResizedInputEvent(width, height));
	}

}
