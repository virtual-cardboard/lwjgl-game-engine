package context.visuals.gui.constraint.dimension;

public class RelativeDimensionConstraint extends GuiDimensionConstraint {

	private float factor;

	public RelativeDimensionConstraint(float factor) {
		this.factor = factor;
	}

	public float getFactor() {
		return factor;
	}

	public void setFactor(float factor) {
		this.factor = factor;
	}

	@Override
	public float calculateValue(float start, float end) {
		return (end - start) * factor;
	}

}
