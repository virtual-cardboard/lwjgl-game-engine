package context;

import java.util.PriorityQueue;

import common.event.GameEvent;
import context.data.GameData;
import context.input.GameInput;
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

	GameContextWrapper wrapper;

	private final GameData data;
	private final GameInput input;
	private final GameLogic logic;
	private final GameVisuals visuals;

	private PriorityQueue<GameEvent> eventQueue = new PriorityQueue<>();

	/**
	 * Takes in a data, input, logic, and visuals. Then sets the context references
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

	public void init() {
		data.init();
		input.init();
		logic.init();
		visuals.init();
	}

	public GameContextWrapper getWrapper() {
		return wrapper;
	}

}
