package context.input.inputdecorator;

import context.input.GameInputBuffer;
import context.input.event.AbstractGameInputEvent;
import context.input.event.KeyPressedInputEvent;
import context.input.event.KeyReleasedInputEvent;
import context.input.event.MouseMovedInputEvent;
import context.input.event.MousePressedInputEvent;
import context.input.event.MouseReleasedInputEvent;
import context.input.event.MouseScrolledInputEvent;
import state.user.LocalUser;

/**
 * An input decorator tries to decorate raw inputs from various sources into
 * input events that our game engine understands. It sends the events to a
 * {@link GameInputBuffer}.
 * 
 * @author Jay
 *
 */
public class GameInputDecorator {

	private GameInputBuffer inputBuffer;

	public void setGameInputBuffer(GameInputBuffer inputBuffer) {
		this.inputBuffer = inputBuffer;
	}

	protected final void pushEventToInputBuffer(AbstractGameInputEvent event) {
		inputBuffer.add(event);
	}

	/**
	 * Decorates a key pressed event into an {@link AbstractGameInputEvent} and
	 * documents it into the input buffer.
	 * 
	 * @param keyCode the key code of the key pressed
	 */
	public void decorateKeyPressed(int keyCode) {
		pushEventToInputBuffer(new KeyPressedInputEvent(System.currentTimeMillis(), LocalUser.localUser, keyCode));
	}

	/**
	 * Decorates a key released event into an {@link AbstractGameInputEvent} and
	 * documents it into the input buffer.
	 * 
	 * @param keyCode the key code of the key released
	 */
	public void decorateKeyReleased(int keyCode) {
		pushEventToInputBuffer(new KeyReleasedInputEvent(System.currentTimeMillis(), LocalUser.localUser, keyCode));
	}

	/**
	 * Decorates a mouse moved event or mouse dragged event into an
	 * {@link AbstractGameInputEvent} and documents it into the input buffer.
	 * 
	 * @param mouseX the new x-coordinate of the cursor
	 * @param mouseY the new y-coordinate of the cursor
	 */
	public void decorateMouseMoved(int mouseX, int mouseY) {
		pushEventToInputBuffer(new MouseMovedInputEvent(System.currentTimeMillis(), LocalUser.localUser, mouseX, mouseY));
	}

	/**
	 * Decorates a mouse pressed event into an {@link AbstractGameInputEvent} and
	 * documents it into the input buffer.
	 * 
	 * @param mouseButton the key code of the mouse pressed
	 */
	public void decorateMousePressed(int mouseButton, int mouseX, int mouseY) {
		pushEventToInputBuffer(new MousePressedInputEvent(System.currentTimeMillis(), LocalUser.localUser, mouseButton, mouseX, mouseY));
	}

	/**
	 * Decorates a mouse released event into an {@link AbstractGameInputEvent} and
	 * documents it into the input buffer.
	 * 
	 * @param mouseButton the key code of the mouse released
	 */
	public void decorateMouseReleased(int mouseButton, int mouseX, int mouseY) {
		pushEventToInputBuffer(new MouseReleasedInputEvent(System.currentTimeMillis(), LocalUser.localUser, mouseButton, mouseX, mouseY));
	}

	/**
	 * Decorates a mouse scrolled event into an {@link AbstractGameInputEvent} and
	 * documents it into the input buffer.
	 * 
	 * @param amount the amount scrolled
	 */
	public void decorateMouseScrolled(int amount) {
		pushEventToInputBuffer(new MouseScrolledInputEvent(System.currentTimeMillis(), LocalUser.localUser, amount));
	}

	// Mouse Moved
	// Mouse Scrolled

}
