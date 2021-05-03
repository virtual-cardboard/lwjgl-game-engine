package context.logic;

import context.ContextPart;
import context.data.GameData;
import context.input.GameInput;

/**
 * A bundle part that updates data and handles user input.
 * 
 * See {@link GameData} and {@link GameInput}.
 * 
 * @author Jay
 *
 */
public abstract class GameLogic extends ContextPart {

	public abstract void update();

	public void init() {
	}

}
