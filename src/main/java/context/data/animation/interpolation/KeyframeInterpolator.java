package context.data.animation.interpolation;

import java.util.List;

import context.data.animation.Keyframe;
import context.data.animation.SkeletonNodeState;
import context.data.animation.SkeletonState;

public class KeyframeInterpolator {

	public SkeletonState interpolateKeyframe(Keyframe k1, Keyframe k2, float time) {
		int k1Time = k1.getTime();
		// Calculate the factor, which is a value between 0 and 1, to interpolate by
		// (linear by default)
		float factor = (time - k1Time) / (k2.getTime() - k1Time);
		float interpolationFactor = k1.getInterpolationType().toInterpolationFactor(factor);
		return interpolateState(k1.getSkeletonState(), k2.getSkeletonState(), interpolationFactor);
	}

	private SkeletonState interpolateState(SkeletonState s1, SkeletonState s2, float factor) {
		SkeletonState interpolated = new SkeletonState();
		List<SkeletonNodeState> interpolatedNodeStates = interpolated.getSkeletonNodeStates();
		List<SkeletonNodeState> nodeStates1 = s1.getSkeletonNodeStates();
		List<SkeletonNodeState> nodeStates2 = s2.getSkeletonNodeStates();
		for (int i = 0; i < nodeStates1.size(); i++) {
			SkeletonNodeState nodeState1 = nodeStates1.get(i);
			float distance1 = nodeState1.getDistance();
			float rotation1 = nodeState1.getRotation();
			SkeletonNodeState nodeState2 = nodeStates2.get(i);
			float distance2 = nodeState2.getDistance();
			float rotation2 = nodeState2.getRotation();

			float angleDiff = Angles.angleDifference(rotation1, rotation2);
			float interpolatedDistance = distance1 + (distance2 - distance1) * factor;
			float interpolatedRotation = rotation1 + angleDiff * factor;
			SkeletonNodeState interpolatedState = new SkeletonNodeState(interpolatedDistance, interpolatedRotation);
			interpolatedNodeStates.add(interpolatedState);
		}
		return interpolated;
	}

}