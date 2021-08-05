package context.visuals.gui.constraint.dimension;

public class AspectDimensionConstraint extends GuiDimensionConstraint {

	private GuiDimensionConstraint dimensionConstraint;
	private float factor;

	public AspectDimensionConstraint(float factor, GuiDimensionConstraint dimensionConstraint) {
		setDimensionConstraint(dimensionConstraint);
		this.factor = factor;
	}

	public GuiDimensionConstraint getDimensionConstraint() {
		return dimensionConstraint;
	}

	public void setDimensionConstraint(GuiDimensionConstraint dimensionConstraint) {
		if (dimensionConstraint instanceof AspectDimensionConstraint) {
			throw new RuntimeException("Cannot have two aspect dimension constraints on a GUI.");
		}
		this.dimensionConstraint = dimensionConstraint;
	}

	public float getFactor() {
		return factor;
	}

	public void setFactor(float factor) {
		this.factor = factor;
	}

	@Override
	public float calculateValue(float start, float end) {
		// TODO
		return dimensionConstraint.calculateValue(start, end) * factor;
	}

}
