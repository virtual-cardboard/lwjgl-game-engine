package context.visuals.gui.constraint.dimension;

import context.visuals.gui.constraint.GuiConstraint;

public abstract class GuiDimensionConstraint extends GuiConstraint {

	private float lastValue;

	@Override
	public final float get(float start, float end) {
		lastValue = doGet(start, end);
		return lastValue;
	}

	public abstract float doGet(float start, float end);

	float lastValue() {
		return lastValue;
	}

}
