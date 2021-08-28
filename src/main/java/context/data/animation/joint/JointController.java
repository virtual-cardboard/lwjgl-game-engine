package context.data.animation.joint;

import java.util.ArrayList;
import java.util.List;

public abstract class JointController {

	private List<Joint> joints;

	public JointController() {
		this(new ArrayList<>());
	}

	public JointController(List<Joint> joints) {
		this.joints = joints;
	}

	protected List<Joint> getJoints() {
		return joints;
	}

	public abstract void updateJoints();

}
