package context.logic;

import context.ContextPart;
import context.data.GameData;
import context.input.AbstractGameInput;

/**
 * A bundle part that updates data and handles user input.
 * 
 * See {@link GameData} and {@link AbstractGameInput}.
 * 
 * @author Jay
 *
 */
public abstract class AbstractGameLogic extends ContextPart {

	public abstract void update();

	public void init() {
	}

}
