package bundle.input.eventhandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import bundle.input.event.AbstractGameInputEvent;
import bundle.input.event.KeyPressedInputEvent;
import bundle.input.event.KeyReleasedInputEvent;
import bundle.input.event.MouseMovedInputEvent;
import bundle.input.event.MousePressedInputEvent;
import bundle.input.event.MouseReleasedInputEvent;
import bundle.input.event.MouseScrolledInputEvent;

public class GameInputEventHandlerFactory {

	@SuppressWarnings("rawtypes")
	private Map<Class<? extends AbstractGameInputEvent>, ArrayList<? extends AbstractGameInputEventHandler>> eventClassToHandlers;

	public GameInputEventHandlerFactory(ArrayList<KeyPressedInputEventHandler> keyPressedInputHandlers,
			ArrayList<KeyReleasedInputEventHandler> keyReleasedInputHandlers,
			ArrayList<MouseMovedInputEventHandler> mouseMovedInputHandlers,
			ArrayList<MousePressedInputEventHandler> mousePressedInputHandlers,
			ArrayList<MouseReleasedInputEventHandler> mouseReleasedInputHandlers,
			ArrayList<MouseScrolledInputEventHandler> mouseScrolledInputHandlers) {
		eventClassToHandlers = new HashMap<>();
		eventClassToHandlers.put(KeyPressedInputEvent.class, keyPressedInputHandlers);
		eventClassToHandlers.put(KeyReleasedInputEvent.class, keyReleasedInputHandlers);
		eventClassToHandlers.put(MouseMovedInputEvent.class, mouseMovedInputHandlers);
		eventClassToHandlers.put(MousePressedInputEvent.class, mousePressedInputHandlers);
		eventClassToHandlers.put(MouseReleasedInputEvent.class, mouseReleasedInputHandlers);
		eventClassToHandlers.put(MouseScrolledInputEvent.class, mouseScrolledInputHandlers);
	}

	@SuppressWarnings("rawtypes")
	public ArrayList<? extends AbstractGameInputEventHandler> getHandlers(
			Class<? extends AbstractGameInputEvent> eventClass) {
		return eventClassToHandlers.get(eventClass);
	}

}
