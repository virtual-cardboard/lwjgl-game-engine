package context;

import java.net.DatagramSocket;
import java.util.Queue;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import common.loader.GameLoader;
import common.timestep.AudioUpdater;
import common.timestep.GameLogicTimer;
import common.timestep.WindowFrameUpdater;
import context.input.GameInputHandlerRunnable;
import context.input.event.GameInputEvent;
import context.input.event.PacketReceivedInputEvent;
import context.input.networking.packet.PacketModel;
import context.logic.TimeAccumulator;

/**
 * A container for a game context to make switching game contexts thread-safe.
 * 
 * Without a wrapper, swapping contexts requires updating every reference to the
 * context. Aside from being cumbersome, synchronization errors can occur
 * because swapping is not instantaneous.
 * 
 * With the context wrapper, swapping contexts becomes thread-safe. Every
 * reference to the context is replaced with a reference to the wrapper, so
 * swapping contexts is instantaneous with one reference update.
 * 
 * @author Jay, Lunkle
 *
 */
public final class GameContextWrapper {

	/**
	 * Context that is being wrapped.
	 */
	private GameContext context;

	/**
	 * This read-write lock is not a lock on the context itself. The read and write
	 * lock is on the accessibility of the context reference.
	 */
	private final ReadWriteLock contextLock = new ReentrantReadWriteLock();

	private final Queue<GameInputEvent> inputBuffer;
	private Queue<PacketReceivedInputEvent> networkReceiveBuffer;
	private final TimeAccumulator accumulator;
	private final WindowFrameUpdater windowFrameUpdater;
	private AudioUpdater audioUpdater;
	private final GameLoader loader;
	private final Queue<PacketModel> networkSendBuffer;
	private DatagramSocket socket;
	private GLContext glContext;
	private ResourcePack resourcePack;

	/**
	 * A constructor that takes in the context, input buffer, logic timer,
	 * windowFrameUpdateTimer, and loader.
	 * 
	 * @param inputBuffer            {@link Queue} of {@link GameInputEvent}s
	 * @param networkSendBuffer
	 * @param accumulator            the logic timer
	 * @param windowFrameUpdateTimer the {@link WindowFrameUpdater}
	 * @param logicTimer
	 * @param audioUpdater
	 * @param inputHandler
	 * @param loader                 the {@link GameLoader}
	 */
	public GameContextWrapper(Queue<GameInputEvent> inputBuffer, Queue<PacketReceivedInputEvent> networkReceiveBuffer, Queue<PacketModel> networkSendBuffer,
			TimeAccumulator accumulator, WindowFrameUpdater windowFrameUpdateTimer, GameLogicTimer logicTimer, AudioUpdater audioUpdater,
			GameInputHandlerRunnable inputHandler, GLContext glContext, GameLoader loader, DatagramSocket socket) {
		this.socket = socket;
		this.inputBuffer = inputBuffer;
		this.networkReceiveBuffer = networkReceiveBuffer;
		this.networkSendBuffer = networkSendBuffer;
		this.accumulator = accumulator;
		this.windowFrameUpdater = windowFrameUpdateTimer;
		this.audioUpdater = audioUpdater;
		if (windowFrameUpdateTimer != null) {
			windowFrameUpdateTimer.setWrapper(this);
		}
		if (inputHandler != null) {
			inputHandler.setWrapper(this);
		}
		if (audioUpdater != null) {
			audioUpdater.setWrapper(this);
		}
		logicTimer.setWrapper(this);
		this.glContext = glContext;
		this.resourcePack = new ResourcePack(glContext);
		this.loader = loader;
	}

	/**
	 * Swaps current context for the provided context. Puts a write lock on the
	 * reference.
	 * 
	 * @param context the context to transition to
	 */
	public void transition(GameContext context) {
		synchronized (contextLock.writeLock()) {
			context.setWrapper(this);
			context.init(inputBuffer, networkReceiveBuffer, loader);
			this.context = context;
		}
	}

	/**
	 * The read lock is on the context reference, not the context itself. The
	 * context is still mutable and the caller is allowed to mutate the context. The
	 * read lock is to prevent
	 * 
	 * @return game context
	 */
	public GameContext context() {
		synchronized (contextLock.readLock()) {
			return context;
		}
	}

	public Queue<GameInputEvent> inputBuffer() {
		return inputBuffer;
	}

	public void sendPacket(PacketModel packet) {
		networkSendBuffer.add(packet);
	}

	public Queue<PacketModel> networkSend() {
		return networkSendBuffer;
	}

	public TimeAccumulator accumulator() {
		return accumulator;
	}

	public WindowFrameUpdater windowFrameUpdater() {
		return windowFrameUpdater;
	}

	public short socketPort() {
		if (socket == null) {
			return 0;
		}
		return (short) socket.getLocalPort();
	}

	public void terminate() {
		if (audioUpdater != null) {
			System.out.println("Ending audio updater");
			audioUpdater.end();
		}
		if (context != null) {
			context.terminate();
		}
		if (loader != null) {
			loader.terminate();
		}
		resourcePack.terminate();
	}

	public GLContext glContext() {
		return glContext;
	}

	public ResourcePack resourcePack() {
		return resourcePack;
	}

}
