package context;

import java.util.PriorityQueue;
import java.util.Queue;

import common.event.GameEvent;
import common.loader.IOLoader;
import context.data.GameData;
import context.input.GameInput;
import context.input.event.GameInputEvent;
import context.input.event.PacketReceivedInputEvent;
import context.input.networking.packet.PacketModel;
import context.logic.GameLogic;
import context.visuals.GameVisuals;
import context.visuals.lwjgl.builder.GLObjectFactory;

/**
 * A grouping of the four context parts needed in a game: <br>
 * - Data <br>
 * - Input <br>
 * - Logic <br>
 * - Visuals
 * 
 * @author Jay
 *
 */
public final class GameContext {

	private GameContextWrapper wrapper;

	private final GameData data;
	private final GameInput input;
	private final GameLogic logic;
	private final GameVisuals visuals;

	private PriorityQueue<GameEvent> eventQueue = new PriorityQueue<>();

	/**
	 * Takes in a data, input, logic, and visuals, then sets the context references
	 * of each of its parts to itself.
	 * 
	 * @param data    GameData
	 * @param input   GameInput
	 * @param logic   GameLogic
	 * @param visuals GameVisuals
	 */
	public GameContext(GameData data, GameInput input, GameLogic logic, GameVisuals visuals) {
		this.data = data;
		this.input = input;
		this.logic = logic;
		this.visuals = visuals;

		this.data.setContext(this);
		this.input.setContext(this);
		this.logic.setContext(this);
		this.visuals.setContext(this);
	}

	public GameData data() {
		return data;
	}

	public GameInput input() {
		return input;
	}

	public GameLogic logic() {
		return logic;
	}

	public GameVisuals visuals() {
		return visuals;
	}

	/**
	 * Initializes the context parts with the given input event buffer.
	 * <p>
	 * Called by {@link GameContextWrapper} whenever
	 * {@link GameContextWrapper#transition(GameContext) transition}ing to this
	 * context.
	 * <p>
	 * 
	 * @param inputEventBuffer     the input buffer
	 * @param networkReceiveBuffer the receive buffer
	 * @param loader               the {@link Loader}
	 * @param glObjectFactory      the {@link GLObjectFactory}
	 */
	public void init(Queue<GameInputEvent> inputEventBuffer, Queue<PacketReceivedInputEvent> networkReceiveBuffer, IOLoader loader,
			GLObjectFactory glObjectFactory) {
		data.doInit(loader);
		input.doInit(inputEventBuffer, networkReceiveBuffer, eventQueue);
		logic.doInit(eventQueue, loader);
		visuals.doInit(loader, glObjectFactory);
	}

	public GameContextWrapper wrapper() {
		return wrapper;
	}

	void setWrapper(GameContextWrapper wrapper) {
		this.wrapper = wrapper;
	}

	public void sendPacket(PacketModel packet) {
		wrapper.sendPacket(packet);
	}

	public short socketPort() {
		return wrapper.socketPort();
	}

	/**
	 * Called when the {@link GameContextWrapper} terminates.
	 * <p>
	 * Note: context parts terminate in the reverse order they are
	 * {@link #init(Queue, Queue) init}ialized in.
	 */
	public void terminate() {
		visuals.terminate();
		logic.terminate();
		input.terminate();
		data.terminate();
	}

}
