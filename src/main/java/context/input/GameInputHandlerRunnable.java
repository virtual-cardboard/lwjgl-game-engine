package context.input;

import context.GameContextWrapper;
import context.logic.TimeAccumulator;
import engine.common.timestep.TimestepTimer;

public class GameInputHandlerRunnable extends TimestepTimer {

	private GameContextWrapper wrapper;
	private boolean isDone = false;

	public GameInputHandlerRunnable() {
		super(30, new TimeAccumulator());
	}

	@Override
	public void update() {
		wrapper.context().input().handleAll();
	}

	public void terminate() {
		isDone = true;
	}

	@Override
	protected boolean endCondition() {
		return isDone;
	}

	public void setWrapper(GameContextWrapper wrapper) {
		this.wrapper = wrapper;
	}

}
