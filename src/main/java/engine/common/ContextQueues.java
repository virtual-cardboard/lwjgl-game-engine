package engine.common;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import context.ContextPart;
import context.GameWindow;
import context.input.GameInput;
import context.input.event.GameInputEvent;
import context.input.event.PacketReceivedInputEvent;
import context.input.networking.UDPReceiver;
import context.logic.GameLogic;
import engine.GameEngine;
import engine.common.event.GameEvent;

/**
 * A container for all of the {@link ContextPart}-to-{@link ContextPart} queues.
 *
 * @author Jay
 */
public final class ContextQueues {

	/**
	 * The queue that {@link GameInput} would put {@link GameEvent}s into. These {@link GameEvent}s should then be
	 * handled in {@link GameLogic}.
	 */
	public final Queue<GameEvent> inputToLogic = new ConcurrentLinkedQueue<>();
	public final Queue<GameEvent> logicToVisuals = new ConcurrentLinkedQueue<>();
	public final Queue<GameEvent> logicToAudio = new ConcurrentLinkedQueue<>();
	/**
	 * The input buffer is initialized by the {@link GameEngine} upon initialization. The {@link GameWindow} has
	 * <code>Callbacks</code> that write to this input buffer. {@link GameInput} reads from the input buffer and
	 * transforms them into other {@link GameEvent}s in the {@link GameInput#handleAll()} function.
	 */
	public final Queue<GameInputEvent> inputEventBuffer;
	/**
	 * The network buffer is initialized by the {@link GameEngine} upon initialization if networking is enabled. The
	 * {@link UDPReceiver} writes to this network buffer whenever a {@link DatagramPacket} arrives on the {@link
	 * DatagramSocket} initialized in the {@link GameEngine}. {@link GameInput} reads from the network buffer and
	 * transforms them into other {@link GameEvent}s in the {@link GameInput#handleAll()} function.
	 */
	public final Queue<PacketReceivedInputEvent> networkReceiveBuffer;
	
	public ContextQueues(Queue<GameInputEvent> inputEventBuffer, Queue<PacketReceivedInputEvent> networkReceiveBuffer) {
		this.inputEventBuffer = inputEventBuffer;
		this.networkReceiveBuffer = networkReceiveBuffer;
	}

	public void pushEventToLogic(GameEvent e) {
		inputToLogic.add(e);
	}

	public void pushEventFromLogic(GameEvent e) {
		logicToVisuals.add(e);
		logicToAudio.add(e);
	}

}
