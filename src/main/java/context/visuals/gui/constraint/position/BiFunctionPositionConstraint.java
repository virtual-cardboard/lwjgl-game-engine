package context.visuals.gui.constraint.position;

import java.util.function.BiFunction;

public class BiFunctionPositionConstraint extends GuiPositionConstraint {

	private BiFunction<Float, Float, Float> biFunction;

	public BiFunctionPositionConstraint(BiFunction<Float, Float, Float> biFunction) {
		this.biFunction = biFunction;
	}

	@Override
	public float get(float start, float end) {
		return biFunction.apply(start, end);
	}

}
