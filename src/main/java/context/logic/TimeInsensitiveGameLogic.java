package context.logic;

import context.GameContext;

/**
 * 
 * Use this when you want a one-use logic context part that pauses the
 * {@code GameLogicTimer}.
 * 
 * @author Lunkle
 *
 */
public abstract class TimeInsensitiveGameLogic extends GameLogic {

	public TimeInsensitiveGameLogic() {
		timeSensitive = false;
	}

	@Override
	public final void update() {
		logic();
		context().transition(nextContext());
	}

	protected abstract void logic();

	protected abstract GameContext nextContext();

}
