package context;

import java.util.ArrayList;

import context.data.GameData;
import context.input.AbstractGameInput;
import context.logic.AbstractGameLogic;
import context.visuals.AbstractGameVisuals;

/**
 * A collection of the four bundle parts needed in a game:
 * <p>
 * - Data
 * <p>
 * - Input
 * <p>
 * - Logic
 * <p>
 * - Visuals
 * 
 * @author Jay
 *
 */
public class GameContext {

	protected GameContextWrapper wrapper;

	protected GameData data;
	protected AbstractGameInput input;
	protected AbstractGameLogic logic;
	protected AbstractGameVisuals visuals;

	/**
	 * A constructor that takes in a game data, input, logic, and visuals. Then, the
	 * constructor sets the bundles of each of its bundle parts to itself.
	 * 
	 * @param data    GameData
	 * @param input   GameInput
	 * @param logic   GameLogic
	 * @param visuals GameVisuals
	 */
	public GameContext(GameData data, AbstractGameInput input, AbstractGameLogic logic, AbstractGameVisuals visuals) {
		this.data = data;
		this.input = input;
		this.logic = logic;
		this.visuals = visuals;

		this.data.setBundle(this);
		this.input.setBundle(this);
		this.logic.setBundle(this);
		this.visuals.setBundle(this);
	}

	public ArrayList<ContextPart> getBundleParts() {
		ArrayList<ContextPart> parts = new ArrayList<>();
		parts.add(data);
		parts.add(input);
		parts.add(logic);
		parts.add(visuals);
		return parts;
	}

	public GameData getData() {
		return data;
	}

	public void setData(GameData data) {
		this.data = data;
	}

	public AbstractGameInput getInput() {
		return input;
	}

	public void setInput(AbstractGameInput input) {
		this.input = input;
	}

	public AbstractGameLogic getLogic() {
		return logic;
	}

	public void setLogic(AbstractGameLogic logic) {
		this.logic = logic;
	}

	public AbstractGameVisuals getVisuals() {
		return visuals;
	}

	public void setVisuals(AbstractGameVisuals visuals) {
		this.visuals = visuals;
	}

	public void initBundleParts() {
		data.init();
		input.init();
		logic.init();
		visuals.init();
	}

	public GameContextWrapper getWrapper() {
		return wrapper;
	}

}
