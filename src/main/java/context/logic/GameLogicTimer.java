package context.logic;

import context.GameContextWrapper;

public class GameLogicTimer implements Runnable {

	private GameContextWrapper wrapper;

	private boolean isDone = false;

	private long framesElapsed = 1;
	private float targetFrameRate = 10f;
	private float targetFrameTime = 1000.0f / targetFrameRate;
	private TimeAccumulator accumulator;

	private long currentTime;

	public GameLogicTimer(GameContextWrapper wrapper, TimeAccumulator accumulator) {
		this.wrapper = wrapper;
		this.accumulator = accumulator;
		accumulator.setFrameTime(targetFrameTime);
	}

	@Override
	public void run() {
		currentTime = System.currentTimeMillis();
		while (!isDone) {
			// Conditional updates if time is up
			fixedTimeStepUpdate();
			// Yielding thread in case other threads need a chance to shine
			Thread.yield();
		}
	}

	private void fixedTimeStepUpdate() {
		long newTime = System.currentTimeMillis();
		long frameTime = newTime - currentTime;

		// The following if check is to make sure we don't fall into the spiral of death
		if (frameTime >= 1000) {
			frameTime = 1000;
		}
		currentTime = newTime;
		accumulator.add(frameTime);
		GameLogic gameLogic = wrapper.getContext().getLogic();

		// Updating as many times as needed to make up for any lag
		while (accumulator.getAccumulation() >= targetFrameTime) {
			gameLogic.update();
			accumulator.sub(targetFrameTime);
			framesElapsed++;
		}
	}

	public void setContextWrapper(GameContextWrapper contextWrapper) {
		this.wrapper = contextWrapper;
	}

	public void end() {
		isDone = true;
	}

	public long getFramesElapsed() {
		return framesElapsed;
	}

}
