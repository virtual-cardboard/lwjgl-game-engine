package engine.common.timestep;

import java.util.concurrent.CountDownLatch;

import context.GameContextWrapper;
import context.logic.GameLogic;
import context.logic.TimeAccumulator;

public class GameLogicTimer extends TimestepTimer {

	private GameContextWrapper wrapper;
	private boolean isDone = false;
	private final CountDownLatch contextCountDownLatch;

	public GameLogicTimer(float tickRate, TimeAccumulator accumulator, CountDownLatch contextCountDownLatch) {
		super(tickRate, accumulator);
		this.setWrapper(wrapper);
		this.contextCountDownLatch = contextCountDownLatch;
	}

	@Override
	protected void update() {
		GameLogic gameLogic = wrapper.context().logic();
		gameLogic.doUpdate();
		if (!gameLogic.timeSensitive()) {
			clearAccumulator();
		}
	}

	@Override
	protected void startActions() {
		try {
			contextCountDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void end() {
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
