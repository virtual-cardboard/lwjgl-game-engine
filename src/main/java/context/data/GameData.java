package context.data;

import context.ContextPart;

/**
 * A context part that stores data.
 * 
 * @author Jay
 *
 */
public abstract class GameData extends ContextPart {

	public final void init() {
		doInit();
	}

	protected void doInit() {
	}
}
