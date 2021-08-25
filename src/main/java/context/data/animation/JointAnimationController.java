package context.data.animation;

import java.util.List;

import context.data.animation.joint.Joint;
import context.data.animation.joint.JointController;
import context.data.animation.skeleton.Skeleton;

public class JointAnimationController extends JointController {

	private Animation animation;
	private Skeleton skeleton;

	public JointAnimationController(Animation animation, Skeleton skeleton) {
		this.animation = animation;
		this.skeleton = skeleton;
	}

	@Override
	public void doUpdateJoints(List<Joint> joints) {
		// TODO
	}

	public Animation getAnimation() {
		return animation;
	}

	public Skeleton getSkeleton() {
		return skeleton;
	}

}
