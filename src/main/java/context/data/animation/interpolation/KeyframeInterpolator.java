package context.data.animation.interpolation;

import static context.data.animation.interpolation.Angles.angleDifference;
import static context.data.animation.interpolation.InterpolationType.SMOOTH;
import static java.lang.Math.PI;

import java.util.List;

import common.math.Vector2f;
import context.data.animation.Keyframe;
import context.data.animation.SkeletonState;

public class KeyframeInterpolator {

	private static final SplineInterpolator SPLINE_INTERPOLATOR = new SplineInterpolator();

	private KeyframeInterpolator() {
	}

	public static SkeletonState interpolateSmoothKeyframe(List<Keyframe> keyframes, int keyframeIndex, int currentTime) {
		int start = keyframeIndex;
		int end = keyframeIndex;
		// Iterate through keyframes to find the start and end of group of SMOOTH
		// keyframes
		for (int i = keyframeIndex; i >= 0 && keyframes.get(i).getInterpolationType() == SMOOTH; start = i--)
			continue;
		for (int i = keyframeIndex; i < keyframes.size() && keyframes.get(i).getInterpolationType() == SMOOTH; end = i++)
			continue;
		end++;
		if (end - start <= 2) {
			// Too few keyframes to use spline interpolator
			Keyframe k1 = keyframes.get(start);
			Keyframe k2 = keyframes.get(start + 1);
			return interpolateKeyframe(k1, k2, currentTime);
		}

		SkeletonState interpolated = new SkeletonState();

		float[] times = extractTimes(keyframes, start, end); // Extract times
		int numDistances = keyframes.get(start).getSkeletonState().getDistances().size();
		for (int i = 0; i < numDistances; i++) {
			float[] distances = extractDistances(keyframes, start, end, i);
			float[] rotations = extractRotations(keyframes, start, end, i);
			interpolated.getDistances().add(SPLINE_INTERPOLATOR.interpolate(times, distances, currentTime));
			interpolated.getRotations().add(toRadius(SPLINE_INTERPOLATOR.interpolate(times, rotations, currentTime)));
		}
		float[] xs = extractXs(keyframes, start, end);
		float[] ys = extractYs(keyframes, start, end);
		interpolated.getRootPosition().x = SPLINE_INTERPOLATOR.interpolate(times, xs, currentTime);
		interpolated.getRootPosition().y = SPLINE_INTERPOLATOR.interpolate(times, ys, currentTime);
		return interpolated;
	}

	private static float[] extractTimes(List<Keyframe> keyframes, int start, int end) {
		float[] times = new float[end - start];
		for (int i = 0; i < times.length; i++) {
			times[i] = keyframes.get(i + start).getTime();
		}
		return times;
	}

	private static float[] extractDistances(List<Keyframe> keyframes, int start, int end, int index) {
		float[] distances = new float[end - start];
		for (int i = 0; i < distances.length; i++) {
			distances[i] = keyframes.get(i + start).getSkeletonState().getDistances().get(index);
		}
		return distances;
	}

	private static float[] extractRotations(List<Keyframe> keyframes, int start, int end, int index) {
		float[] rotations = new float[end - start];
		float prev = 0;
		for (int i = 0; i < rotations.length; i++) {
			rotations[i] = prev + angleDifference(prev, keyframes.get(i + start).getSkeletonState().getRotations().get(index));
		}
		return rotations;
	}

	private static float[] extractXs(List<Keyframe> keyframes, int start, int end) {
		float[] xs = new float[end - start];
		for (int i = 0; i < xs.length; i++) {
			xs[i] = keyframes.get(i + start).getSkeletonState().getRootPosition().x;
		}
		return xs;
	}

	private static float[] extractYs(List<Keyframe> keyframes, int start, int end) {
		float[] ys = new float[end - start];
		for (int i = 0; i < ys.length; i++) {
			ys[i] = keyframes.get(i + start).getSkeletonState().getRootPosition().y;
		}
		return ys;
	}

	private static float toRadius(float d) {
		return (float) ((d % (2 * PI) + (2 * PI)) % (2 * PI));
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
		float angleDiff = angleDifference(r1, r2);
		return r1 + angleDiff * factor;
	}

	private static float interpolateDistance(float d1, float d2, float factor) {
		return d1 + (d2 - d1) * factor;
	}

}