package bundle.input;

import java.util.ArrayList;

import bundle.AbstractBundlePart;
import bundle.input.event.AbstractGameInputEvent;
import bundle.input.eventhandler.AbstractGameInputEventHandler;
import bundle.input.eventhandler.GameInputEventHandlerFactory;
import bundle.input.eventhandler.KeyPressedInputEventHandler;
import bundle.input.eventhandler.KeyReleasedInputEventHandler;
import bundle.input.eventhandler.MouseMovedInputEventHandler;
import bundle.input.eventhandler.MousePressedInputEventHandler;
import bundle.input.eventhandler.MouseReleasedInputEventHandler;
import bundle.input.eventhandler.MouseScrolledInputEventHandler;
import bundle.input.eventhandler.guimousehandler.GUIMouseMovedInputEventHandler;
import bundle.input.eventhandler.guimousehandler.GUIMousePressedInputEventHandler;
import bundle.input.eventhandler.guimousehandler.GUIMouseReleasedInputEventHandler;
import common.coordinates.PixelCoordinates;

/**
 * A bundle part that handles user input.
 * 
 * @author Jay
 *
 */
public abstract class AbstractGameInput extends AbstractBundlePart {

	private GameInputBuffer inputBuffer;
	private GameInputEventHandlerFactory inputEventHandlerFactory;

	public void handleAll() {
		while (!inputBuffer.isEmpty()) {
			handleEvent(inputBuffer.getNext());
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void handleEvent(AbstractGameInputEvent inputEvent) {
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
		mouseMovedInputEventHandlers.add(new GUIMouseMovedInputEventHandler(getBundle().getData()));
		mouseMovedInputEventHandlers.add((event) -> {
			getBundle().getData().setCursorCoordinates(new PixelCoordinates(event.getMouseX(), event.getMouseY()));
			return false;
		});
		return mouseMovedInputEventHandlers;
	}

	protected final ArrayList<MousePressedInputEventHandler> setAllMousePressedInputHandler() {
		ArrayList<MousePressedInputEventHandler> mousePressedInputEventHandlers = new ArrayList<>();
		mousePressedInputEventHandlers.add(new GUIMousePressedInputEventHandler(getBundle().getData()));
		ArrayList<MousePressedInputEventHandler> setMousePressedInputHandler = setMousePressedInputHandlers();
		if (setMousePressedInputHandler != null) {
			mousePressedInputEventHandlers.addAll(setMousePressedInputHandler);
		}
		return mousePressedInputEventHandlers;
	}

	protected final ArrayList<MouseReleasedInputEventHandler> setAllMouseReleasedInputHandler() {
		ArrayList<MouseReleasedInputEventHandler> mouseReleasedInputEventHandlers = new ArrayList<>();
		mouseReleasedInputEventHandlers.add(new GUIMouseReleasedInputEventHandler(getBundle().getData()));
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
		inputBuffer = getBundle().getWrapper().getInputBuffer();
		ArrayList<KeyPressedInputEventHandler> kp = setAllKeyPressedInputHandler();
		ArrayList<KeyReleasedInputEventHandler> kr = setAllKeyReleasedInputHandler();
		ArrayList<MouseMovedInputEventHandler> mm = setAllMouseMovedInputHandler();
		ArrayList<MousePressedInputEventHandler> mp = setAllMousePressedInputHandler();
		ArrayList<MouseReleasedInputEventHandler> mr = setAllMouseReleasedInputHandler();
		ArrayList<MouseScrolledInputEventHandler> ms = setAllMouseScrolledInputHandler();
		inputEventHandlerFactory = new GameInputEventHandlerFactory(kp, kr, mm, mp, mr, ms);
	}

}
