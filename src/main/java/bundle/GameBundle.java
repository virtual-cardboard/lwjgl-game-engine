package bundle;

import java.util.ArrayList;

import bundle.data.AbstractGameData;
import bundle.input.AbstractGameInput;
import bundle.logic.AbstractGameLogic;
import bundle.visuals.AbstractGameVisuals;

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
public class GameBundle {

	protected GameBundleWrapper wrapper;

	protected AbstractGameData data;
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
	public GameBundle(AbstractGameData data, AbstractGameInput input, AbstractGameLogic logic, AbstractGameVisuals visuals) {
		this.data = data;
		this.input = input;
		this.logic = logic;
		this.visuals = visuals;

		this.data.setBundle(this);
		this.input.setBundle(this);
		this.logic.setBundle(this);
		this.visuals.setBundle(this);
	}

	public ArrayList<AbstractBundlePart> getBundleParts() {
		ArrayList<AbstractBundlePart> parts = new ArrayList<>();
		parts.add(data);
		parts.add(input);
		parts.add(logic);
		parts.add(visuals);
		return parts;
	}

	public AbstractGameData getData() {
		return data;
	}

	public void setData(AbstractGameData data) {
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

	public GameBundleWrapper getWrapper() {
		return wrapper;
	}

}
