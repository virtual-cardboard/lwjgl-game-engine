package context.data.animation.animation;

import java.util.List;

import context.data.animation.Joint;
import context.data.animation.JointController;
import context.data.animation.skeleton.Skeleton;

public class AnimationJointController extends JointController {

	private Animation animation;
	private Skeleton skeleton;

	public AnimationJointController(Animation animation, Skeleton skeleton) {
		this.animation = animation;
		this.skeleton = skeleton;
	}

	@Override
	public void doUpdateJoints(List<Joint> joints) {

	}

}
