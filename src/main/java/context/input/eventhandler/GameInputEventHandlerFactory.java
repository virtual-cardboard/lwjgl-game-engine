package context.input.eventhandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import context.input.event.GameInputEvent;
import context.input.event.KeyPressedInputEvent;
import context.input.event.KeyReleasedInputEvent;
import context.input.event.MouseMovedInputEvent;
import context.input.event.MousePressedInputEvent;
import context.input.event.MouseReleasedInputEvent;
import context.input.event.MouseScrolledInputEvent;

public class GameInputEventHandlerFactory {

	@SuppressWarnings("rawtypes")
	private Map<Class<? extends GameInputEvent>, ArrayList<? extends AbstractGameInputEventHandler>> eventClassToHandlers;

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
			Class<? extends GameInputEvent> eventClass) {
		return eventClassToHandlers.get(eventClass);
	}

}
