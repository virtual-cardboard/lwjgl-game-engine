package context.data.animation.interpolation;

import java.util.List;

import common.math.Vector2f;
import context.data.animation.Keyframe;
import context.data.animation.SkeletonState;

public class KeyframeInterpolator {

	private KeyframeInterpolator() {
	}

	public static SkeletonState interpolateKeyframe(Keyframe k1, Keyframe k2, float time) {
		int k1Time = k1.getTime();
		// Calculate the factor, which is a value between 0 and 1, to interpolate by
		// (linear by default)
		float factor = (time - k1Time) / (k2.getTime() - k1Time);
		float interpolationFactor = k1.getInterpolationType().toInterpolationFactor(factor);
		return interpolateState(k1.getSkeletonState(), k2.getSkeletonState(), interpolationFactor);
	}

	private static SkeletonState interpolateState(SkeletonState s1, SkeletonState s2, float factor) {
		SkeletonState interpolated = new SkeletonState();
		List<Float> interpolatedRotations = interpolated.getRotations();
		List<Float> interpolatedDistances = interpolated.getDistances();
		List<Float> rotations1 = s1.getRotations();
		List<Float> distances1 = s1.getDistances();
		List<Float> rotations2 = s2.getRotations();
		List<Float> distances2 = s2.getDistances();

		// Interpolate rotations and distances
		for (int i = 0; i < rotations1.size(); i++) {
			float r1 = rotations1.get(i);
			float d1 = distances1.get(i);
			float r2 = rotations2.get(i);
			float d2 = distances2.get(i);
			interpolatedRotations.add(interpolateRotation(r1, r2, factor));
			interpolatedDistances.add(interpolateDistance(d1, d2, factor));
		}

		// Interpolate root position
		Vector2f s1RootPos = s1.getRootPosition();
		Vector2f s2RootPos = s2.getRootPosition();
		interpolated.getRootPosition().set(s1RootPos.copy().add(s2RootPos.copy().sub(s1RootPos).scale(factor)));
		return interpolated;
	}

	private static float interpolateRotation(float r1, float r2, float factor) {
		float angleDiff = Angles.angleDifference(r1, r2);
		return r1 + angleDiff * factor;
	}

	private static float interpolateDistance(float d1, float d2, float factor) {
		return d1 + (d2 - d1) * factor;
	}

}