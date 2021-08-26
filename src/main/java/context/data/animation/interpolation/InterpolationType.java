package context.data.animation.interpolation;

import java.util.function.Function;

public enum InterpolationType {

	LINEAR(Float::floatValue),
	EASE_IN(x -> (float) Math.pow(x, 4)),
	EASE_OUT(x -> (float) (1 - Math.pow(1 - x, 4))),
	EASE_IN_OUT(x -> (float) (x < 0.5 ? 8 * Math.pow(x, 4) : 1 - Math.pow(-2 * x + 2, 4) / 2)),
	SMOOTH(Float::floatValue);

	private Function<Float, Float> factorToInterpolationFactor;

	private InterpolationType(Function<Float, Float> factorToInterpolationFactor) {
		this.factorToInterpolationFactor = factorToInterpolationFactor;
	}

	public float toInterpolationFactor(float factor) {
		return factorToInterpolationFactor.apply(factor);
	}

}
