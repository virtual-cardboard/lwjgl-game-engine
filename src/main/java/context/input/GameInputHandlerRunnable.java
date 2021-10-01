package context.input;

import common.timestep.TimestepTimer;
import context.GameContextWrapper;
import context.logic.TimeAccumulator;

public class GameInputHandlerRunnable extends TimestepTimer {

	private GameContextWrapper wrapper;
	private boolean isDone = false;

	public GameInputHandlerRunnable(GameContextWrapper wrapper) {
		super(30, new TimeAccumulator());
		this.wrapper = wrapper;
	}

	@Override
	public void doUpdate() {
		wrapper.context().input().handleAll();
	}

	public void terminate() {
		isDone = true;
	}

	@Override
	protected boolean endCondition() {
		return isDone;
	}

}
