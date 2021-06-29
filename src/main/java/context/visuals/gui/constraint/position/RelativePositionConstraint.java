package context.visuals.gui.constraint.position;

public class RelativePositionConstraint extends GuiPositionConstraint {

	private float factor;

	/**
	 * Creates a {@link RelativePositionConstraint} with a given factor. The factor
	 * is usually between 0.0f and 1.0f.
	 * 
	 * @param factor the factor
	 */
	public RelativePositionConstraint(float factor) {
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
		return start + (end - start) * factor;
	}

}
