package context.logic;

import context.AbstractBundlePart;
import context.data.AbstractGameData;
import context.input.AbstractGameInput;

/**
 * A bundle part that updates data and handles user input.
 * 
 * See {@link AbstractGameData} and {@link AbstractGameInput}.
 * 
 * @author Jay
 *
 */
public abstract class AbstractGameLogic extends AbstractBundlePart {

	public abstract void update();

	public void init() {
	}

}
