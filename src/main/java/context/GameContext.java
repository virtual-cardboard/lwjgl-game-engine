package context;

import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

import common.event.GameEvent;
import common.loader.GameLoader;
import context.data.GameData;
import context.input.GameInput;
import context.input.event.GameInputEvent;
import context.input.event.PacketReceivedInputEvent;
import context.input.networking.packet.PacketModel;
import context.logic.GameLogic;
import context.visuals.GameVisuals;

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

	private PriorityBlockingQueue<GameEvent> inputToLogicEventQueue = new PriorityBlockingQueue<>();
	private PriorityBlockingQueue<GameEvent> logicToVisualsEventQueue = new PriorityBlockingQueue<>();

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
	 * @param loader               the {@link GameLoader}
	 */
	public void init(Queue<GameInputEvent> inputEventBuffer, Queue<PacketReceivedInputEvent> networkReceiveBuffer, GameLoader loader) {
		data.setComponents(loader);
		input.setComponents(inputEventBuffer, networkReceiveBuffer, inputToLogicEventQueue);
		logic.setComponents(inputToLogicEventQueue, logicToVisualsEventQueue, loader);
		visuals.setComponents(logicToVisualsEventQueue, loader, resourcePack());
		data.init();
		input.init();
		logic.init();
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

	public GLContext glContext() {
		return wrapper.glContext();
	}

	public ResourcePack resourcePack() {
		return wrapper.resourcePack();
	}

	public Queue<PacketModel> networkSend() {
		return wrapper.networkSend();
	}

	public void transition(GameContext context) {
		wrapper.transition(context);
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
