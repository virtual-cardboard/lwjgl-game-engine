package context.data.animation;

import static context.data.animation.interpolation.KeyframeInterpolator.interpolateKeyframe;
import static context.data.animation.interpolation.KeyframeInterpolator.interpolateSmoothKeyframe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import common.math.Vector2f;
import context.data.animation.interpolation.InterpolationType;
import context.data.animation.joint.Joint;
import context.data.animation.joint.JointController;
import context.data.animation.skeleton.Skeleton;
import context.data.animation.skeleton.SkeletonNode;

public class AnimationJointController extends JointController {

	private Animation animation;
	private Skeleton skeleton;
	private int currentTime;

	public AnimationJointController(Animation animation, Skeleton skeleton, Joint... joints) {
		this(animation, skeleton, new ArrayList<>(Arrays.asList(joints)));
	}

	public AnimationJointController(Animation animation, Skeleton skeleton, List<Joint> joints) {
		super(joints);
		this.animation = animation;
		this.skeleton = skeleton;
	}

	@Override
	public void updateJoints() {
		List<Keyframe> keyframes = animation.getKeyframes();
		int index = indexOf(keyframes, currentTime);

		SkeletonState skeletonState = null;
		if (index >= 0) {
			// A keyframe exists at the current time
			skeletonState = keyframes.get(index).getSkeletonState();
		} else if (-index - 1 > keyframes.size()) {
			// The current time is past the last keyframe
			skeletonState = keyframes.get(keyframes.size() - 1).getSkeletonState();
		} else {
			// The current time is between two keyframes, so we must interpolate
			Keyframe k1 = keyframes.get(-index - 2);
			Keyframe k2 = keyframes.get(-index - 1);
			if (k1.getInterpolationType() == InterpolationType.SMOOTH) {
				skeletonState = interpolateSmoothKeyframe(keyframes, -index - 2, currentTime);
			} else {
				skeletonState = interpolateKeyframe(k1, k2, currentTime);
			}
		}
		updateJointsUsingSkeletonState(getJoints(), skeletonState);
	}

	private void updateJointsUsingSkeletonState(List<Joint> joints, SkeletonState skeletonState) {
		Joint parent = joints.get(0);
		for (int i = 0; i < skeleton.getRootNode().getChildren().size(); i++) {
			doUpdateJointsUsingSkeletonState(skeleton.getRootNode(), joints, skeletonState.getRotations(), skeletonState.getDistances(), i + 1, parent,
					parent.rotation());
		}
	}

	private void doUpdateJointsUsingSkeletonState(SkeletonNode node, List<Joint> joints, List<Float> rotations, List<Float> distances, int index,
			Joint parent, float parentAbsoluteRotation) {
		Joint joint = joints.get(index);
		float rotation = rotations.get(index);
		float distance = distances.get(index);
		Vector2f relativeOffset = Vector2f.fromAngleLength(parentAbsoluteRotation + rotation, distance);
		joint.setRotation(rotation);
		joint.setPosition(parent.position().add(relativeOffset));
		for (SkeletonNode child : node.getChildren()) {
			index++;
			doUpdateJointsUsingSkeletonState(child, joints, rotations, distances, index, joint, parentAbsoluteRotation + rotation);
		}
	}

	// Getters and setters

	public Animation getAnimation() {
		return animation;
	}

	public Skeleton getSkeleton() {
		return skeleton;
	}

	public int getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(int currentTime) {
		this.currentTime = currentTime;
	}

	private static int indexOf(List<Keyframe> keyframes, int time) {
		int l = 0, r = keyframes.size() - 1;
		int m = -1;
		while (l <= r) {
			m = l + (r - l) / 2;
			int t = keyframes.get(m).getTime();
			if (t == time) return m;
			if (t < time) l = m++ + 1;
			else r = m - 1;
		}
		return -m - 1;
	}

}
