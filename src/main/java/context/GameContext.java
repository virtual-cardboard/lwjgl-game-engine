package context;

import java.util.Queue;

import context.audio.GameAudio;
import context.data.GameData;
import context.input.GameInput;
import context.input.event.GameInputEvent;
import context.input.event.PacketReceivedInputEvent;
import context.logic.GameLogic;
import context.visuals.GameVisuals;
import engine.common.QueueGroup;
import engine.common.loader.GameLoader;
import engine.common.networking.packet.PacketModel;
import engine.common.timestep.GameLogicTimer;

/**
 * A grouping of the five context parts needed in a game: <br>
 * - Audio <br>
 * - Data <br>
 * - Input <br>
 * - Logic <br>
 * - Visuals
 *
 * @author Jay
 */
public final class GameContext {

	private GameContextWrapper wrapper;

	private final GameAudio audio;
	private final GameData data;
	private final GameInput input;
	private final GameLogic logic;
	private final GameVisuals visuals;

	/**
	 * Takes in the five context parts, then sets the context references of each of
	 * its parts to itself.
	 *
	 * @param audio   GameAudio
	 * @param data    GameData
	 * @param input   GameInput
	 * @param logic   GameLogic
	 * @param visuals GameVisuals
	 */
	public GameContext(GameAudio audio, GameData data, GameInput input, GameLogic logic, GameVisuals visuals) {
		this.audio = audio;
		this.data = data;
		this.input = input;
		this.logic = logic;
		this.visuals = visuals;

		this.audio.setContext(this);
		this.data.setContext(this);
		this.input.setContext(this);
		this.logic.setContext(this);
		this.visuals.setContext(this);
	}

	public GameAudio audio() {
		return audio;
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
	 * The GameContextWrapper initializes the non-visuals context parts before
	 * swapping the context in. WindowFrameUpdater only initializes the new visuals
	 * once the new context has been swapped in, so the visuals is always updated
	 * last.
	 *
	 * @param inputEventBuffer     the input buffer
	 * @param networkReceiveBuffer the receive buffer
	 * @param loader               the {@link GameLoader}
	 */
	public void init(Queue<GameInputEvent> inputEventBuffer, Queue<PacketReceivedInputEvent> networkReceiveBuffer, GameLogicTimer timer, GameLoader loader) {
		data.setComponents(loader);
		QueueGroup queueGroup = new QueueGroup(inputEventBuffer, networkReceiveBuffer);
		input.setComponents(queueGroup);
		audio.setComponents(queueGroup, loader);
		logic.setComponents(timer, queueGroup, loader);
		visuals.setComponents(queueGroup, loader);
		audio.init();
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

	ResourcePack resourcePack() {
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
	 * {@link #init(Queue, Queue, GameLogicTimer, GameLoader)}  initialized} in.
	 */
	public void terminate() {
		visuals.terminate();
		logic.terminate();
		audio.terminate();
		input.terminate();
		data.terminate();
	}

}
