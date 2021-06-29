package context.visuals.gui.constraint.position;

import context.visuals.gui.constraint.dimension.GuiDimensionConstraint;

public class CenterPositionConstraint extends GuiPositionConstraint {

	private GuiDimensionConstraint dimensionConstraint;

	public CenterPositionConstraint(GuiDimensionConstraint dimensionConstraint) {
		this.dimensionConstraint = dimensionConstraint;
	}

	@Override
	public float calculateValue(float start, float end) {
		return (start + end - dimensionConstraint.calculateValue(start, end)) * 0.5f;
	}

}
