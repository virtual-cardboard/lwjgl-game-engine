package common.timestep;

import context.logic.TimeAccumulator;

public abstract class TimestepTimer implements Runnable {

	/**
	 * The target number of frames per second.
	 */
	private float targetFrameRate;
	private float targetFrameTime;

	private final TimeAccumulator accumulator;

	private long framesElapsed = 1;
	private long currentTime;

	/**
	 * Instantiates a <code>TimestepTimer</code> with the given
	 * <code>frameRate</code> and {@link TimeAccumulator}.
	 * 
	 * @param frameRate   the target number of frames per second
	 * @param accumulator the <code>TimeAccumulator</code>
	 */
	public TimestepTimer(float frameRate, TimeAccumulator accumulator) {
		this.accumulator = accumulator;
		setFrameRate(frameRate);
	}

	/**
	 * Instantiates a <code>TimestepTimer</code> with the given
	 * <code>frameRate</code> and the default {@link TimeAccumulator}.
	 * 
	 * @param frameRate the target number of frames per second
	 */
	public TimestepTimer(float frameRate) {
		this(frameRate, new TimeAccumulator());
	}

	@Override
	public final void run() {
		startActions();
		currentTime = System.currentTimeMillis();
		try {
			while (!endCondition()) {
				// Conditional updates if time is up
				doUpdate();
				// Yielding thread in case other threads need a chance to shine
				Thread.yield();
			}
		} finally {
			endActions();
		}
	}

	/**
	 * Calls {@link #update() doUpdate()} enough times to match the
	 * {@link #targetFrameRate targetFrameRate}.
	 */
	private void doUpdate() {
		long newTime = System.currentTimeMillis();
		long frameTime = newTime - currentTime;

		// The following if check is to make sure we don't fall into the spiral of death
		if (frameTime >= 1000) {
			frameTime = 1000;
		}
		currentTime = newTime;
		accumulator.add(frameTime);

		// Updating as many times as needed to make up for any lag
		while (shouldUpdate()) {
			update();
			accumulator.sub(targetFrameTime);
			framesElapsed++;
		}
	}

	public void clearAccumulator() {
		accumulator.clear();
	}

	/**
	 * Whether or not {@link #update()} should be called.
	 * 
	 * @return if <code>update()</code> should be called
	 */
	protected boolean shouldUpdate() {
		return accumulator.getAccumulation() >= targetFrameTime;
	}

	/**
	 * Updates the game. This function is called every tick in {@link #doUpdate()
	 * update()}.
	 */
	protected abstract void update();

	/**
	 * @return true if the {@link TimestepTimer} should end, false otherwise.
	 */
	protected abstract boolean endCondition();

	/**
	 * Actions performed before the {@link TimestepTimer} starts updating.
	 */
	protected void startActions() {
	}

	/**
	 * Actions performed after the {@link TimestepTimer} stops updating.
	 */
	protected void endActions() {
	}

	public float getFrameRate() {
		return targetFrameRate;
	}

	public void setFrameRate(float frameRate) {
		this.targetFrameRate = frameRate;
		targetFrameTime = 1000 / frameRate;
		accumulator.clear();
		accumulator.setFrameTime(targetFrameTime);
	}

	public long getFramesElapsed() {
		return framesElapsed;
	}

}
