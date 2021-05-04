package context.input.decorator;

import java.util.Queue;

import context.input.GameInputEventBuffer;
import context.input.event.GameInputEvent;
import context.input.event.KeyPressedInputEvent;
import context.input.event.KeyReleasedInputEvent;
import context.input.event.MouseMovedInputEvent;
import context.input.event.MousePressedInputEvent;
import context.input.event.MouseReleasedInputEvent;
import context.input.event.MouseScrolledInputEvent;

/**
 * An input decorator decorates raw inputs from GLFW into input events that our
 * game engine understands. It sends the events to a
 * {@link GameInputEventBuffer}.
 * 
 * @author Lunkle
 *
 */
public final class GameInputDecorator {

	private final Queue<GameInputEvent> inputBuffer;

	public GameInputDecorator(Queue<GameInputEvent> inputBuffer) {
		this.inputBuffer = inputBuffer;
	}

	/**
	 * Decorates a key pressed event into an {@link KeyPressedInputEvent} and adds
	 * it into the input buffer.
	 * 
	 * @param keyCode the key code of the key pressed
	 */
	public void decorateKeyPressed(int keyCode) {
		inputBuffer.add(new KeyPressedInputEvent(keyCode));
	}

	/**
	 * Decorates a key released event into an {@link KeyReleasedInputEvent} and adds
	 * it into the input buffer.
	 * 
	 * @param keyCode the key code of the key released
	 */
	public void decorateKeyReleased(int keyCode) {
		inputBuffer.add(new KeyReleasedInputEvent(keyCode));
	}

	/**
	 * Decorates a mouse moved event or mouse dragged event into an
	 * {@link MouseMovedInputEvent} and adds it into the input buffer.
	 * 
	 * @param mouseX the new x-coordinate of the cursor
	 * @param mouseY the new y-coordinate of the cursor
	 */
	public void decorateMouseMoved(int mouseX, int mouseY) {
		inputBuffer.add(new MouseMovedInputEvent(mouseX, mouseY));
	}

	/**
	 * Decorates a mouse pressed event into an {@link MousePressedInputEvent} and
	 * adds it into the input buffer.
	 * 
	 * @param mouseButton the key code of the mouse pressed
	 * @param mouseX      the x-coordinate of the cursor at press
	 * @param mouseY      the y-coordinate of the cursor at press
	 */
	public void decorateMousePressed(int mouseButton, int mouseX, int mouseY) {
		inputBuffer.add(new MousePressedInputEvent(mouseButton, mouseX, mouseY));
	}

	/**
	 * Decorates a mouse released event into an {@link MouseReleasedInputEvent} and
	 * adds it into the input buffer.
	 * 
	 * @param mouseButton the key code of the mouse released
	 * @param mouseX      the x-coordinate of the cursor at release
	 * @param mouseY      the y-coordinate of the cursor at release
	 */
	public void decorateMouseReleased(int mouseButton, int mouseX, int mouseY) {
		inputBuffer.add(new MouseReleasedInputEvent(mouseButton, mouseX, mouseY));
	}

	/**
	 * Decorates a mouse scrolled event into an {@link MouseScrolledInputEvent} and
	 * adds it into the input buffer.
	 * 
	 * @param amount the amount scrolled
	 */
	public void decorateMouseScrolled(int amount) {
		inputBuffer.add(new MouseScrolledInputEvent(amount));
	}

}
