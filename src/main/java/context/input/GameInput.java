package context.input;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import common.GameInputEventHandler;
import common.event.GameEvent;
import context.ContextPart;
import context.GameWindow;
import context.input.event.FrameResizedInputEvent;
import context.input.event.GameInputEvent;
import context.input.event.KeyPressedInputEvent;
import context.input.event.KeyReleasedInputEvent;
import context.input.event.KeyRepeatedInputEvent;
import context.input.event.MouseMovedInputEvent;
import context.input.event.MousePressedInputEvent;
import context.input.event.MouseReleasedInputEvent;
import context.input.event.MouseScrolledInputEvent;
import context.input.event.PacketReceivedInputEvent;
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

	public final void init(Queue<GameInputEvent> inputEventBuffer, Queue<PacketReceivedInputEvent> networkReceiveBuffer, PriorityQueue<GameEvent> eventQueue) {
		this.inputEventBuffer = inputEventBuffer;
		this.networkReceiveBuffer = networkReceiveBuffer;
		this.eventQueue = eventQueue;
		frameResizedInputEventHandlers.add(new GameInputEventHandler<>(new RootGuiUpdaterFunction(getContext())));
		mouseMovedInputEventHandlers.add(new GameInputEventHandler<>(new GameCursorMovedUpdaterFunction(cursor)));
		mousePressedInputEventHandlers.add(new GameInputEventHandler<>(new GameCursorPressedUpdaterFunction(cursor)));
		mouseReleasedInputEventHandlers.add(new GameInputEventHandler<>(new GameCursorReleasedUpdaterFunction(cursor)));
		doInit();
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

	protected void addKeyPressedFunction(GameInputEventHandler<KeyPressedInputEvent> function) {
		keyPressedInputEventHandlers.add(function);
	}

	protected void addKeyReleasedFunction(GameInputEventHandler<KeyReleasedInputEvent> function) {
		keyReleasedInputEventHandlers.add(function);
	}

	protected void addMousePressedFunction(GameInputEventHandler<MousePressedInputEvent> function) {
		mousePressedInputEventHandlers.add(function);
	}

	protected void addMouseReleasedFunction(GameInputEventHandler<MouseReleasedInputEvent> function) {
		mouseReleasedInputEventHandlers.add(function);
	}

	protected void addMouseMovedFunction(GameInputEventHandler<MouseMovedInputEvent> function) {
		mouseMovedInputEventHandlers.add(function);
	}

	protected void addMouseScrolledFunction(GameInputEventHandler<MouseScrolledInputEvent> function) {
		mouseScrolledInputEventHandlers.add(function);
	}

	protected void addFrameResizedFunction(GameInputEventHandler<FrameResizedInputEvent> function) {
		frameResizedInputEventHandlers.add(function);
	}

	protected void addKeyRepeatedFunction(GameInputEventHandler<KeyRepeatedInputEvent> function) {
		keyRepeatedInputEventHandlers.add(function);
	}

	protected void addPacketReceivedFunction(GameInputEventHandler<PacketReceivedInputEvent> function) {
		packetReceivedInputEventHandlers.add(function);
	}

	public GameCursor getCursor() {
		return cursor;
	}

}
