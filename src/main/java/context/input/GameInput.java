package context.input;

import java.util.ArrayList;

import common.coordinates.IntCoordinates;
import context.ContextPart;
import context.input.event.GameInputEvent;
import context.input.eventhandler.AbstractGameInputEventHandler;
import context.input.eventhandler.GameInputEventHandlerFactory;
import context.input.eventhandler.KeyPressedInputEventHandler;
import context.input.eventhandler.KeyReleasedInputEventHandler;
import context.input.eventhandler.MouseMovedInputEventHandler;
import context.input.eventhandler.MousePressedInputEventHandler;
import context.input.eventhandler.MouseReleasedInputEventHandler;
import context.input.eventhandler.MouseScrolledInputEventHandler;
import context.input.eventhandler.guimousehandler.GUIMouseMovedInputEventHandler;
import context.input.eventhandler.guimousehandler.GUIMousePressedInputEventHandler;
import context.input.eventhandler.guimousehandler.GUIMouseReleasedInputEventHandler;
import context.input.mouse.GameMouse;

/**
 * A bundle part that handles user input.
 * 
 * @author Jay
 *
 */
public abstract class GameInput extends ContextPart {

	private GameInputEventBuffer inputBuffer;
	private GameInputEventHandlerFactory inputEventHandlerFactory;
	private GameMouse mouse;

	public void handleAll() {
		while (!inputBuffer.isEmpty()) {
			handleEvent(inputBuffer.getNext());
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void handleEvent(GameInputEvent inputEvent) {
		ArrayList<? extends AbstractGameInputEventHandler> handlers = inputEventHandlerFactory.getHandlers(inputEvent.getClass());
		for (AbstractGameInputEventHandler handler : handlers) {
			boolean handled = handler.handle(inputEvent);
			if (handled)
				break;
		}
	}

	protected abstract ArrayList<KeyPressedInputEventHandler> setKeyPressedInputHandlers();

	protected abstract ArrayList<KeyReleasedInputEventHandler> setKeyReleasedInputHandlers();

	protected abstract ArrayList<MouseMovedInputEventHandler> setMouseMovedInputHandlers();

	protected abstract ArrayList<MouseReleasedInputEventHandler> setMouseReleasedInputHandlers();

	protected abstract ArrayList<MousePressedInputEventHandler> setMousePressedInputHandlers();

	protected abstract ArrayList<MouseScrolledInputEventHandler> setMouseScrolledInputHandlers();

	protected final ArrayList<KeyPressedInputEventHandler> setAllKeyPressedInputHandler() {
		ArrayList<KeyPressedInputEventHandler> keyPressedInputEventHandlers = new ArrayList<>();
		ArrayList<KeyPressedInputEventHandler> setKeyPressedInputHandler = setKeyPressedInputHandlers();
		if (setKeyPressedInputHandler != null) {
			keyPressedInputEventHandlers.addAll(setKeyPressedInputHandler);
		}
		return keyPressedInputEventHandlers;
	}

	protected final ArrayList<KeyReleasedInputEventHandler> setAllKeyReleasedInputHandler() {
		ArrayList<KeyReleasedInputEventHandler> keyReleasedInputEventHandlers = new ArrayList<>();
		ArrayList<KeyReleasedInputEventHandler> setKeyReleasedInputHandler = setKeyReleasedInputHandlers();
		if (setKeyReleasedInputHandler != null) {
			keyReleasedInputEventHandlers.addAll(setKeyReleasedInputHandler);
		}
		return keyReleasedInputEventHandlers;
	}

	protected final ArrayList<MouseMovedInputEventHandler> setAllMouseMovedInputHandler() {
		ArrayList<MouseMovedInputEventHandler> mouseMovedInputEventHandlers = new ArrayList<>();
		ArrayList<MouseMovedInputEventHandler> setMouseMovedInputHandler = setMouseMovedInputHandlers();
		if (setMouseMovedInputHandler != null) {
			mouseMovedInputEventHandlers.addAll(setMouseMovedInputHandler);
		}
		mouseMovedInputEventHandlers.add(new GUIMouseMovedInputEventHandler(getContext().getData()));
		mouseMovedInputEventHandlers.add((event) -> {
			mouse.setCursorCoordinates(new IntCoordinates(event.getMouseX(), event.getMouseY()));
			return false;
		});
		return mouseMovedInputEventHandlers;
	}

	protected final ArrayList<MousePressedInputEventHandler> setAllMousePressedInputHandler() {
		ArrayList<MousePressedInputEventHandler> mousePressedInputEventHandlers = new ArrayList<>();
		mousePressedInputEventHandlers.add(new GUIMousePressedInputEventHandler(getContext().getData()));
		ArrayList<MousePressedInputEventHandler> setMousePressedInputHandler = setMousePressedInputHandlers();
		if (setMousePressedInputHandler != null) {
			mousePressedInputEventHandlers.addAll(setMousePressedInputHandler);
		}
		return mousePressedInputEventHandlers;
	}

	protected final ArrayList<MouseReleasedInputEventHandler> setAllMouseReleasedInputHandler() {
		ArrayList<MouseReleasedInputEventHandler> mouseReleasedInputEventHandlers = new ArrayList<>();
		mouseReleasedInputEventHandlers.add(new GUIMouseReleasedInputEventHandler(getContext().getData()));
		ArrayList<MouseReleasedInputEventHandler> setMouseReleasedInputHandler = setMouseReleasedInputHandlers();
		if (setMouseReleasedInputHandler != null) {
			mouseReleasedInputEventHandlers.addAll(setMouseReleasedInputHandler);
		}
		return mouseReleasedInputEventHandlers;
	}

	protected final ArrayList<MouseScrolledInputEventHandler> setAllMouseScrolledInputHandler() {
		ArrayList<MouseScrolledInputEventHandler> mouseScrolledInputEventHandlers = new ArrayList<>();
		ArrayList<MouseScrolledInputEventHandler> setMouseScrolledInputHandler = setMouseScrolledInputHandlers();
		if (setMouseScrolledInputHandler != null) {
			mouseScrolledInputEventHandlers.addAll(setMouseScrolledInputHandler);
		}
		return mouseScrolledInputEventHandlers;
	}

	public void init() {
		inputBuffer = getContext().getWrapper().getInputBuffer();
		ArrayList<KeyPressedInputEventHandler> kp = setAllKeyPressedInputHandler();
		ArrayList<KeyReleasedInputEventHandler> kr = setAllKeyReleasedInputHandler();
		ArrayList<MouseMovedInputEventHandler> mm = setAllMouseMovedInputHandler();
		ArrayList<MousePressedInputEventHandler> mp = setAllMousePressedInputHandler();
		ArrayList<MouseReleasedInputEventHandler> mr = setAllMouseReleasedInputHandler();
		ArrayList<MouseScrolledInputEventHandler> ms = setAllMouseScrolledInputHandler();
		inputEventHandlerFactory = new GameInputEventHandlerFactory(kp, kr, mm, mp, mr, ms);
	}

}
