package context.input;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.function.Function;
import java.util.function.Predicate;

import common.event.GameEvent;
import context.ContextPart;
import context.GameWindow;
import context.input.event.*;
import context.input.mouse.GameCursor;
import context.logic.GameLogic;
import engine.GameEngine;

/**
 * A context part that handles user input. It transforms raw
 * {@link GameInputEvent}s into other {@link GameEvent}s.
 * 
 * @author Jay
 *
 */
public abstract class GameInput extends ContextPart {

	/**
	 * The input buffer is initialized by the {@link GameEngine} upon
	 * initialization. The {@link GameWindow} has <code>Callbacks</code> that write
	 * to this input buffer. {@link GameInput} reads from the input buffer and
	 * transforms them into other {@link GameEvent}s in the {@link #handleAll()}
	 * function.
	 */
	private Queue<GameInputEvent> inputEventBuffer;

	/**
	 * The network buffer is initialized by the {@link GameEngine} upon
	 * initialization if networking is enabled. The {@link UDPReader} writes to this
	 * network buffer whenever a {@link DatagramPacket} arrives on the
	 * {@link DatagramSocket} initialized in the {@link GameEngine}.
	 * {@link GameInput} reads from the network buffer and transforms them into
	 * other {@link GameEvent}s in the {@link #handleAll()} function.
	 */
	private Queue<PacketReceivedInputEvent> networkReceiveBuffer;

	/**
	 * The queue that {@link GameInput} would put {@link GameEvent}s into. These
	 * {@link GameEvent}s should then be handled in {@link GameLogic}.
	 */
	private Queue<GameEvent> eventQueue;

	/** {@link List} of {@link FrameResizedInputEvent} handlers. */
	private List<GameInputEventHandler<FrameResizedInputEvent>> frameResizedInputEventHandlers = new ArrayList<>();
	/** {@link List} of {@link KeyPressedInputEvent} handlers. */
	private List<GameInputEventHandler<KeyPressedInputEvent>> keyPressedInputEventHandlers = new ArrayList<>();
	/** {@link List} of {@link KeyReleasedInputEvent} handlers. */
	private List<GameInputEventHandler<KeyReleasedInputEvent>> keyReleasedInputEventHandlers = new ArrayList<>();
	/** {@link List} of {@link KeyRepeatedInputEvent} handlers. */
	private List<GameInputEventHandler<KeyRepeatedInputEvent>> keyRepeatedInputEventHandlers = new ArrayList<>();
	/** {@link List} of {@link MouseMovedInputEvent} handlers. */
	private List<GameInputEventHandler<MouseMovedInputEvent>> mouseMovedInputEventHandlers = new ArrayList<>();
	/** {@link List} of {@link MousePressedInputEvent} handlers. */
	private List<GameInputEventHandler<MousePressedInputEvent>> mousePressedInputEventHandlers = new ArrayList<>();
	/** {@link List} of {@link MouseReleasedInputEvent} handlers. */
	private List<GameInputEventHandler<MouseReleasedInputEvent>> mouseReleasedInputEventHandlers = new ArrayList<>();
	/** {@link List} of {@link MouseScrolledInputEvent} handlers. */
	private List<GameInputEventHandler<MouseScrolledInputEvent>> mouseScrolledInputEventHandlers = new ArrayList<>();
	/** {@link List} of {@link PacketReceivedInputEvent} handlers. */
	private List<GameInputEventHandler<PacketReceivedInputEvent>> packetReceivedInputEventHandlers = new ArrayList<>();

	/**
	 * The cursor.
	 */
	private GameCursor cursor = new GameCursor();

	public final void doInit(Queue<GameInputEvent> inputEventBuffer, Queue<PacketReceivedInputEvent> networkReceiveBuffer, Queue<GameEvent> eventQueue) {
		this.inputEventBuffer = inputEventBuffer;
		this.networkReceiveBuffer = networkReceiveBuffer;
		this.eventQueue = eventQueue;
		frameResizedInputEventHandlers.add(new GameInputEventHandler<>(new RootGuiUpdaterFunction(context())));
		mouseMovedInputEventHandlers.add(new GameInputEventHandler<>(new GameCursorMovedUpdaterFunction(cursor)));
		mousePressedInputEventHandlers.add(new GameInputEventHandler<>(new GameCursorPressedUpdaterFunction(cursor)));
		mouseReleasedInputEventHandlers.add(new GameInputEventHandler<>(new GameCursorReleasedUpdaterFunction(cursor)));
		init();
	}

	/**
	 * Handles all {@link PacketReceivedInputEvent}s in
	 * {@link #networkReceiveBuffer} and all {@link GameInputEvent}s in
	 * {@link #inputEventBuffer}.
	 */
	public void handleAll() {
		while (!networkReceiveBuffer.isEmpty()) {
			handleEvent(packetReceivedInputEventHandlers, networkReceiveBuffer.poll());
		}
		while (!inputEventBuffer.isEmpty()) {
			handleEvent(inputEventBuffer.poll());
		}
	}

	/**
	 * Handles an inputEvent using the right {@link List} of
	 * {@link GameInputEventHandler}s.
	 * 
	 * @param inputEvent the {@link GameInputEvent} to handle
	 */
	private void handleEvent(GameInputEvent inputEvent) {
		if (inputEvent instanceof KeyPressedInputEvent) {
			handleEvent(keyPressedInputEventHandlers, (KeyPressedInputEvent) inputEvent);
		} else if (inputEvent instanceof KeyReleasedInputEvent) {
			handleEvent(keyReleasedInputEventHandlers, (KeyReleasedInputEvent) inputEvent);
		} else if (inputEvent instanceof KeyRepeatedInputEvent) {
			handleEvent(keyRepeatedInputEventHandlers, (KeyRepeatedInputEvent) inputEvent);
		} else if (inputEvent instanceof MousePressedInputEvent) {
			handleEvent(mousePressedInputEventHandlers, (MousePressedInputEvent) inputEvent);
		} else if (inputEvent instanceof MouseReleasedInputEvent) {
			handleEvent(mouseReleasedInputEventHandlers, (MouseReleasedInputEvent) inputEvent);
		} else if (inputEvent instanceof MouseMovedInputEvent) {
			handleEvent(mouseMovedInputEventHandlers, (MouseMovedInputEvent) inputEvent);
		} else if (inputEvent instanceof MouseScrolledInputEvent) {
			handleEvent(mouseScrolledInputEventHandlers, (MouseScrolledInputEvent) inputEvent);
		} else if (inputEvent instanceof FrameResizedInputEvent) {
			handleEvent(frameResizedInputEventHandlers, (FrameResizedInputEvent) inputEvent);
		}
	}

	/**
	 * Uses a {@link List} of {@link GameInputEventHandler}s to handle
	 * <code>inputEvent</code>.
	 * 
	 * @param <T>           the type of <code>inputEvent</code>
	 * @param eventHandlers the list of <code>GameInputEventHandler</code>s to use
	 *                      to handle <code>inputEvent</code>
	 * @param inputEvent    the {@link GameInputEvent} to handle
	 */
	private <T extends GameInputEvent> void handleEvent(List<GameInputEventHandler<T>> eventHandlers, T inputEvent) {
		for (int i = 0; i < eventHandlers.size(); i++) {
			GameInputEventHandler<T> eventHandler = eventHandlers.get(i);
			if (eventHandler.isSatisfiedBy(inputEvent)) {
				GameEvent event = eventHandler.apply(inputEvent);
				if (event != null) {
					eventQueue.add(event);
				}
				if (eventHandler.doesConsume()) {
					break;
				}
			}
		}
	}

	protected void addKeyPressedFunction(Function<KeyPressedInputEvent, GameEvent> function) {
		keyPressedInputEventHandlers.add(new GameInputEventHandler<>(function));
	}

	protected void addKeyReleasedFunction(Function<KeyReleasedInputEvent, GameEvent> function) {
		keyReleasedInputEventHandlers.add(new GameInputEventHandler<>(function));
	}

	protected void addMousePressedFunction(Function<MousePressedInputEvent, GameEvent> function) {
		mousePressedInputEventHandlers.add(new GameInputEventHandler<>(function));
	}

	protected void addMouseReleasedFunction(Function<MouseReleasedInputEvent, GameEvent> function) {
		mouseReleasedInputEventHandlers.add(new GameInputEventHandler<>(function));
	}

	protected void addMouseMovedFunction(Function<MouseMovedInputEvent, GameEvent> function) {
		mouseMovedInputEventHandlers.add(new GameInputEventHandler<>(function));
	}

	protected void addMouseScrolledFunction(Function<MouseScrolledInputEvent, GameEvent> function) {
		mouseScrolledInputEventHandlers.add(new GameInputEventHandler<>(function));
	}

	protected void addFrameResizedFunction(Function<FrameResizedInputEvent, GameEvent> function) {
		frameResizedInputEventHandlers.add(new GameInputEventHandler<>(function));
	}

	protected void addKeyRepeatedFunction(Function<KeyRepeatedInputEvent, GameEvent> function) {
		keyRepeatedInputEventHandlers.add(new GameInputEventHandler<>(function));
	}

	protected void addPacketReceivedFunction(Function<PacketReceivedInputEvent, GameEvent> function) {
		packetReceivedInputEventHandlers.add(new GameInputEventHandler<>(function));
	}

	protected void addKeyPressedFunction(Predicate<KeyPressedInputEvent> predicate, Function<KeyPressedInputEvent, GameEvent> function, boolean consumes) {
		keyPressedInputEventHandlers.add(new GameInputEventHandler<>(predicate, function, consumes));
	}

	protected void addKeyReleasedFunction(Predicate<KeyReleasedInputEvent> predicate, Function<KeyReleasedInputEvent, GameEvent> function, boolean consumes) {
		keyReleasedInputEventHandlers.add(new GameInputEventHandler<>(predicate, function, consumes));
	}

	protected void addMousePressedFunction(Predicate<MousePressedInputEvent> predicate, Function<MousePressedInputEvent, GameEvent> function,
			boolean consumes) {
		mousePressedInputEventHandlers.add(new GameInputEventHandler<>(predicate, function, consumes));
	}

	protected void addMouseReleasedFunction(Predicate<MouseReleasedInputEvent> predicate, Function<MouseReleasedInputEvent, GameEvent> function,
			boolean consumes) {
		mouseReleasedInputEventHandlers.add(new GameInputEventHandler<>(predicate, function, consumes));
	}

	protected void addMouseMovedFunction(Predicate<MouseMovedInputEvent> predicate, Function<MouseMovedInputEvent, GameEvent> function, boolean consumes) {
		mouseMovedInputEventHandlers.add(new GameInputEventHandler<>(predicate, function, consumes));
	}

	protected void addMouseScrolledFunction(Predicate<MouseScrolledInputEvent> predicate, Function<MouseScrolledInputEvent, GameEvent> function,
			boolean consumes) {
		mouseScrolledInputEventHandlers.add(new GameInputEventHandler<>(predicate, function, consumes));
	}

	protected void addFrameResizedFunction(Predicate<FrameResizedInputEvent> predicate, Function<FrameResizedInputEvent, GameEvent> function,
			boolean consumes) {
		frameResizedInputEventHandlers.add(new GameInputEventHandler<>(predicate, function, consumes));
	}

	protected void addKeyRepeatedFunction(Predicate<KeyRepeatedInputEvent> predicate, Function<KeyRepeatedInputEvent, GameEvent> function, boolean consumes) {
		keyRepeatedInputEventHandlers.add(new GameInputEventHandler<>(predicate, function, consumes));
	}

	protected void addPacketReceivedFunction(Predicate<PacketReceivedInputEvent> predicate, Function<PacketReceivedInputEvent, GameEvent> function,
			boolean consumes) {
		packetReceivedInputEventHandlers.add(new GameInputEventHandler<>(predicate, function, consumes));
	}

	public GameCursor cursor() {
		return cursor;
	}

}
