package context;

import java.util.PriorityQueue;
import java.util.Queue;

import common.event.GameEvent;
import context.data.GameData;
import context.input.GameInput;
import context.input.event.GameInputEvent;
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

	public GameData getData() {
		return data;
	}

	public GameInput getInput() {
		return input;
	}

	public GameLogic getLogic() {
		return logic;
	}

	public GameVisuals getVisuals() {
		return visuals;
	}

	/**
	 * Initializes the context parts with the given input event buffer.
	 * {@link GameContextWrapper} calls <code>init()</code> after transitioning to
	 * this context.
	 * 
	 * @param inputEventBuffer the input buffer
	 */
	public void init(Queue<GameInputEvent> inputEventBuffer) {
		data.init();
		input.init(inputEventBuffer, eventQueue);
		logic.init(eventQueue);
		visuals.init();
	}

	public GameContextWrapper getWrapper() {
		return wrapper;
	}

	void setWrapper(GameContextWrapper wrapper) {
		this.wrapper = wrapper;
	}

}
