package context.data.animation;

import java.util.List;

import context.data.animation.interpolation.KeyframeInterpolator;
import context.data.animation.joint.Joint;
import context.data.animation.joint.JointController;
import context.data.animation.skeleton.Skeleton;
import context.data.animation.skeleton.SkeletonNode;

public class JointAnimationController extends JointController {

	private static final KeyframeInterpolator KEYFRAME_INTERPOLATOR = new KeyframeInterpolator();

	private Animation animation;
	private Skeleton skeleton;
	private int currentTime;

	public JointAnimationController(Animation animation, Skeleton skeleton) {
		this.animation = animation;
		this.skeleton = skeleton;
	}

	@Override
	public void doUpdateJoints(List<Joint> joints) {
//		animation.getKeyframes()
//		Keyframe k1;
//		Keyframe k2;
//		KEYFRAME_INTERPOLATOR.interpolateKeyframe(k1, k2, currentTime);
	}

	private void doDoUpateJoint(Joint joint, SkeletonNode node) {

	}

	public Animation getAnimation() {
		return animation;
	}

	public Skeleton getSkeleton() {
		return skeleton;
	}

}
