package context.logic;

import common.timestep.TimestepTimer;
import context.GameContextWrapper;

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
