package context.input;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import common.ConditionalFunction;
import common.event.GameEvent;
import context.ContextPart;
import context.input.event.FrameResizedInputEvent;
import context.input.event.GameInputEvent;
import context.input.event.KeyPressedInputEvent;
import context.input.event.KeyReleasedInputEvent;
import context.input.event.KeyRepeatedInputEvent;
import context.input.event.MouseMovedInputEvent;
import context.input.event.MousePressedInputEvent;
import context.input.event.MouseReleasedInputEvent;
import context.input.event.MouseScrolledInputEvent;
import context.input.mouse.GameMouse;

/**
 * A context part that handles user input. It transforms raw
 * {@link GameInputEvent}s into other {@link GameEvent}s.
 * 
 * @author Jay
 *
 */
public abstract class GameInput extends ContextPart {

	/**
	 * The input buffer is populated by the context upon initialization. The
	 * {@link GameInputDecorator} writes to this input buffer. This class reads from
	 * the input buffer and transforms them into other {@link GameEvent}s.
	 */
	private Queue<GameInputEvent> inputEventBuffer;

	private Queue<GameEvent> eventQueue;

	private List<ConditionalFunction<KeyPressedInputEvent, GameEvent>> keyPressedFunctions;
	private List<ConditionalFunction<KeyReleasedInputEvent, GameEvent>> keyReleasedFunctions;
	private List<ConditionalFunction<MousePressedInputEvent, GameEvent>> mousePressedFunctions;
	private List<ConditionalFunction<MouseReleasedInputEvent, GameEvent>> mouseReleasedFunctions;
	private List<ConditionalFunction<MouseMovedInputEvent, GameEvent>> mouseMovedFunctions;
	private List<ConditionalFunction<MouseScrolledInputEvent, GameEvent>> mouseScrolledFunctions;
	private List<ConditionalFunction<FrameResizedInputEvent, GameEvent>> frameResizedFunctions;
	private List<ConditionalFunction<KeyRepeatedInputEvent, GameEvent>> keyRepeatFunctions;
	private List<ConditionalFunction<GameInputEvent, GameEvent>> generalFunctions;

	/**
	 * The mouse is automatically updated by the {@link GameInput}.
	 */
	private GameMouse mouse;

	public void handleAll() {
		while (!inputEventBuffer.isEmpty()) {
			handleEvent(inputEventBuffer.poll());
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void handleEvent(GameInputEvent inputEvent) {

	}

	protected void addKeyPressedFunction(ConditionalFunction<KeyPressedInputEvent, GameEvent> function) {
		keyPressedFunctions.add(function);
	}

	protected void addKeyReleasedFunction(ConditionalFunction<KeyReleasedInputEvent, GameEvent> function) {
		keyReleasedFunctions.add(function);
	}

	protected void addMousePressedFunction(ConditionalFunction<MousePressedInputEvent, GameEvent> function) {
		mousePressedFunctions.add(function);
	}

	protected void addMouseReleasedFunction(ConditionalFunction<MouseReleasedInputEvent, GameEvent> function) {
		mouseReleasedFunctions.add(function);
	}

	protected void addMouseMovedFunction(ConditionalFunction<MouseMovedInputEvent, GameEvent> function) {
		mouseMovedFunctions.add(function);
	}

	protected void addMouseScrolledFunction(ConditionalFunction<MouseScrolledInputEvent, GameEvent> function) {
		mouseScrolledFunctions.add(function);
	}

	protected void addFrameResizedFunction(ConditionalFunction<FrameResizedInputEvent, GameEvent> function) {
		frameResizedFunctions.add(function);
	}

	protected void addKeyRepeatedFunction(ConditionalFunction<KeyRepeatedInputEvent, GameEvent> function) {
		keyRepeatFunctions.add(function);
	}

	protected void addGeneralFunction(ConditionalFunction<GameInputEvent, GameEvent> function) {
		generalFunctions.add(function);
	}

//	mouseMovedInputEventHandlers.add(new GUIMouseMovedInputEventHandler(getContext().getData()));mouseMovedInputEventHandlers.add((event)->
//
//	{
//			mouse.setCursorCoordinates(new IntCoordinates(event.getMouseX(), event.getMouseY()));
//			return false;
//		});

	public final void init(Queue<GameInputEvent> inputEventBuffer, PriorityQueue<GameEvent> eventQueue) {
		this.inputEventBuffer = inputEventBuffer;
		this.eventQueue = eventQueue;
		doInit();
	}

	protected void doInit() {
	}

}
