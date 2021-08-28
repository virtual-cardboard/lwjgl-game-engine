package context.data.animation;

import static context.data.animation.interpolation.KeyframeInterpolator.interpolateKeyframe;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import common.math.Vector2f;
import context.data.animation.joint.Joint;
import context.data.animation.joint.JointController;
import context.data.animation.skeleton.Skeleton;
import context.data.animation.skeleton.SkeletonNode;

public class AnimationJointController extends JointController {

	private Animation animation;
	private Skeleton skeleton;
	private int currentTime;

	public AnimationJointController(Animation animation, Skeleton skeleton, List<Joint> joints) {
		super(joints);
		this.animation = animation;
		this.skeleton = skeleton;
	}

	public AnimationJointController(Animation animation, Skeleton skeleton, Joint... joints) {
		super(Arrays.asList(joints));
		this.animation = animation;
		this.skeleton = skeleton;
	}

	@Override
	public void updateJoints() {
		List<Keyframe> keyframes = animation.getKeyframes();
		int index = Collections.binarySearch(keyframes, new Keyframe(currentTime, null));

		SkeletonState skeletonState = null;
		if (index >= 0) {
			skeletonState = keyframes.get(index).getSkeletonState();
		} else {
			Keyframe k1 = keyframes.get(-index - 2);
			Keyframe k2 = keyframes.get(-index - 1);
			skeletonState = interpolateKeyframe(k1, k2, currentTime);
		}

		updateJointsUsingSkeletonState(getJoints(), skeletonState);
	}

	private void updateJointsUsingSkeletonState(List<Joint> joints, SkeletonState skeletonState) {
		Joint parent = joints.get(0);
		for (int i = 0; i < skeleton.getRootNode().getChildren().size(); i++) {
			doUpdateJointsUsingSkeletonState(skeleton.getRootNode(), joints, skeletonState.getRotations(), skeletonState.getDistances(), i + 1, parent,
					parent.getRotation());
		}
	}

	private void doUpdateJointsUsingSkeletonState(SkeletonNode node, List<Joint> joints, List<Float> rotations, List<Float> distances, int index,
			Joint parent, float parentAbsoluteRotation) {
		Joint joint = joints.get(index);
		float rotation = rotations.get(index);
		float distance = distances.get(index);
		Vector2f relativeOffset = Vector2f.fromAngleLength(parentAbsoluteRotation + rotation, distance);
		joint.setRotation(rotation);
		joint.getPosition().set(parent.getPosition().add(relativeOffset));
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

}
