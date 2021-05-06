package context.input;

import java.util.ArrayList;
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
@SuppressWarnings("rawtypes")
public abstract class GameInput extends ContextPart {

	/**
	 * The input buffer is populated by the context upon initialization. The
	 * {@link GameInputDecorator} writes to this input buffer. This class reads from
	 * the input buffer and transforms them into other {@link GameEvent}s.
	 */
	private Queue<GameInputEvent> inputEventBuffer;

	private Queue<GameEvent> eventQueue;

	private List<ConditionalFunction> keyPressedFunctions = new ArrayList<>();
	private List<ConditionalFunction> keyReleasedFunctions = new ArrayList<>();
	private List<ConditionalFunction> mousePressedFunctions = new ArrayList<>();
	private List<ConditionalFunction> mouseReleasedFunctions = new ArrayList<>();
	private List<ConditionalFunction> mouseMovedFunctions = new ArrayList<>();
	private List<ConditionalFunction> mouseScrolledFunctions = new ArrayList<>();
	private List<ConditionalFunction> frameResizedFunctions = new ArrayList<>();
	private List<ConditionalFunction> keyRepeatedFunctions = new ArrayList<>();

	/**
	 * The mouse is automatically updated by the {@link GameInput}.
	 */
	private GameMouse mouse;

	public void handleAll() {
		while (!inputEventBuffer.isEmpty()) {
			handleEvent(inputEventBuffer.poll());
		}
	}

	@SuppressWarnings("unchecked")
	private void handleEvent(List<ConditionalFunction> functions, GameInputEvent inputEvent) {
		for (ConditionalFunction function : functions) {
			if (function.isSatisfiedBy(inputEvent)) {
				eventQueue.add((GameEvent) function.apply(inputEvent));
				if (function.doesConsume()) {
					break;
				}
			}
		}
	}

	private void handleEvent(GameInputEvent inputEvent) {
		if (inputEvent instanceof KeyPressedInputEvent) {
			handleEvent(keyPressedFunctions, inputEvent);
		} else if (inputEvent instanceof KeyReleasedInputEvent) {
			handleEvent(keyReleasedFunctions, inputEvent);
		} else if (inputEvent instanceof KeyRepeatedInputEvent) {
			handleEvent(keyRepeatedFunctions, inputEvent);
		} else if (inputEvent instanceof MousePressedInputEvent) {
			handleEvent(mousePressedFunctions, inputEvent);
		} else if (inputEvent instanceof MouseReleasedInputEvent) {
			handleEvent(mouseReleasedFunctions, inputEvent);
		} else if (inputEvent instanceof MouseMovedInputEvent) {
			handleEvent(mouseMovedFunctions, inputEvent);
		} else if (inputEvent instanceof MouseScrolledInputEvent) {
			handleEvent(mouseScrolledFunctions, inputEvent);
		} else if (inputEvent instanceof FrameResizedInputEvent) {
			handleEvent(frameResizedFunctions, inputEvent);
		}
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
		keyRepeatedFunctions.add(function);
	}

	public final void init(Queue<GameInputEvent> inputEventBuffer, PriorityQueue<GameEvent> eventQueue) {
		this.inputEventBuffer = inputEventBuffer;
		this.eventQueue = eventQueue;
		doInit();
	}

}
