package context.logic;

import static java.lang.Math.max;

public class TimeAccumulator {

	private float accumulation;
	private float frameTime;

	public float getAccumulation() {
		return accumulation;
	}

	public void setAccumulation(float accumulation) {
		this.accumulation = accumulation;
	}

	public TimeAccumulator add(float amount) {
		accumulation += amount;
		return this;
	}

	public TimeAccumulator sub(float amount) {
		accumulation = max(accumulation - amount, 0);
		return this;
	}

	public void setFrameTime(float frameTime) {
		this.frameTime = frameTime;
	}

	public float alpha() {
		return accumulation / frameTime;
	}

	public void clear() {
		accumulation = 0;
	}

}
