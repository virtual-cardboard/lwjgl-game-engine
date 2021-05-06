package engine;

import common.timestep.TimestepTimer;
import context.GameContextWrapper;
import context.logic.GameLogic;
import context.logic.TimeAccumulator;

public class GameLogicTimer extends TimestepTimer {

	private final GameContextWrapper wrapper;
	private boolean isDone = false;

	public GameLogicTimer(GameContextWrapper wrapper, TimeAccumulator accumulator) {
		super(10, accumulator);
		this.wrapper = wrapper;
	}

	@Override
	protected void doUpdate() {
		GameLogic gameLogic = wrapper.getContext().getLogic();
		gameLogic.update();
	}

	public void end() {
		isDone = true;
	}

	@Override
	protected boolean endCondition() {
		return isDone;
	}

}
