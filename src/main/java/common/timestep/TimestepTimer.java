package common.timestep;

import java.util.concurrent.TimeUnit;

import context.logic.TimeAccumulator;

public abstract class TimestepTimer implements Runnable {

	private float targetFrameRate;
	private float targetFrameTime;

	private final TimeAccumulator accumulator;

	private long framesElapsed = 1;
	private long currentTime;

	public TimestepTimer(float frameRate, TimeAccumulator accumulator) {
		this.accumulator = accumulator;
		setFrameRate(frameRate);
	}

	public TimestepTimer(float frameRate) {
		this(frameRate, new TimeAccumulator());
	}

	@Override
	public final void run() {
		startActions();
		currentTime = System.currentTimeMillis();
		while (!endCondition()) {
			// Conditional updates if time is up
			update();
			// Yielding thread in case other threads need a chance to shine
			Thread.yield();
		}
		endActions();
	}

	private void update() {
		long newTime = System.currentTimeMillis();
		long frameTime = newTime - currentTime;

		// The following if check is to make sure we don't fall into the spiral of death
		if (frameTime >= 1000) {
			frameTime = 1000;
		}
		currentTime = newTime;
		accumulator.add(frameTime);

		// Updating as many times as needed to make up for any lag
		while (accumulator.getAccumulation() >= targetFrameTime) {
			doUpdate();
			accumulator.sub(targetFrameTime);
			framesElapsed++;
		}
	}

	protected abstract void doUpdate();

	protected abstract boolean endCondition();

	protected void startActions() {
	}

	protected void endActions() {
	}

	public float getFrameRate() {
		return targetFrameRate;
	}

	public void setFrameRate(float frameRate) {
		this.targetFrameRate = frameRate;
		targetFrameTime = TimeUnit.SECONDS.toMillis(1) / frameRate;
		accumulator.clear();
		accumulator.setFrameTime(targetFrameTime);
	}

	public long getFramesElapsed() {
		return framesElapsed;
	}
}
