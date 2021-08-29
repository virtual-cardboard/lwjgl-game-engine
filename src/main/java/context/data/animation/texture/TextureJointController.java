package context.data.animation.texture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import context.data.animation.joint.Joint;
import context.data.animation.joint.JointController;

public class TextureJointController extends JointController {

	private List<JointTexture> jointTextures;

	public TextureJointController(List<JointTexture> jointTextures, Joint... joints) {
		this(jointTextures, new ArrayList<>(Arrays.asList(joints)));
	}

	public TextureJointController(List<JointTexture> jointTextures, List<Joint> joints) {
		super(joints);
		this.jointTextures = jointTextures;
	}

	@Override
	public void updateJoints() {
	}

}
