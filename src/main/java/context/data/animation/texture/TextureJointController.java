package context.data.animation.texture;

import java.util.ArrayList;
import java.util.List;

import context.data.animation.joint.Joint;
import context.data.animation.joint.JointController;

public class TextureJointController extends JointController {

	public TextureJointController() {
		this(new ArrayList<>());
	}

	public TextureJointController(List<Joint> joints) {
		super(joints);
	}

	@Override
	public void doUpdateJoints(List<Joint> joints) {
	}

}
