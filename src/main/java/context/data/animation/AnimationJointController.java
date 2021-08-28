package context.data.animation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import common.math.Vector2f;
import context.data.animation.interpolation.KeyframeInterpolator;
import context.data.animation.joint.Joint;
import context.data.animation.joint.JointController;
import context.data.animation.skeleton.Skeleton;
import context.data.animation.skeleton.SkeletonNode;
import context.data.animation.texture.TextureJointController;

public class AnimationJointController extends JointController {

	private static final KeyframeInterpolator KEYFRAME_INTERPOLATOR = new KeyframeInterpolator();

	private Animation animation;
	private Skeleton skeleton;
	private int currentTime;

	private TextureJointController jointTextureController;

	public AnimationJointController(Animation animation, Skeleton skeleton) {
		this(new ArrayList<>(), animation, skeleton);
	}

	public AnimationJointController(List<Joint> joints, Animation animation, Skeleton skeleton) {
		super(joints);
		this.animation = animation;
		this.skeleton = skeleton;
		this.jointTextureController = new TextureJointController(joints);
	}

	@Override
	public void doUpdateJoints(List<Joint> joints) {
		List<Keyframe> keyframes = animation.getKeyframes();
		int index = Collections.binarySearch(keyframes, new Keyframe(currentTime, null));
		SkeletonState skeletonState = null;
		if (index >= 0) {
			skeletonState = keyframes.get(index).getSkeletonState();
		} else {
			Keyframe k1 = keyframes.get(-index - 2);
			Keyframe k2 = keyframes.get(-index - 1);
			skeletonState = KEYFRAME_INTERPOLATOR.interpolateKeyframe(k1, k2, currentTime);
		}
		updateJointsUsingSkeletonState(joints, skeletonState);
		jointTextureController.updateJoints();
	}

	private void updateJointsUsingSkeletonState(List<Joint> joints, SkeletonState skeletonState) {
		Joint parent = joints.get(0);
		for (int i = 0; i < skeleton.getRootNode().getChildren().size(); i++) {
			doUpdateJointsUsingSkeletonState(skeleton.getRootNode(), joints, skeletonState.getSkeletonNodeStates(), i + 1, parent, parent.getRotation());
		}
	}

	private void doUpdateJointsUsingSkeletonState(SkeletonNode node, List<Joint> joints, List<SkeletonNodeState> nodeStates, int index,
			Joint parent, float parentAbsoluteRotation) {
		Joint joint = joints.get(index);
		SkeletonNodeState nodeState = nodeStates.get(index);
		float rotation = nodeState.getRotation();
		float distance = nodeState.getDistance();
		Vector2f relativeOffset = Vector2f.fromAngleLength(parentAbsoluteRotation + rotation, distance);
		joint.setRotation(rotation);
		joint.getPosition().set(parent.getPosition().add(relativeOffset));
		for (SkeletonNode child : node.getChildren()) {
			doUpdateJointsUsingSkeletonState(child, joints, nodeStates, ++index, joint, parentAbsoluteRotation + rotation);
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
