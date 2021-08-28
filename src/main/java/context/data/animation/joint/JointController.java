package context.data.animation.joint;

import java.util.ArrayList;
import java.util.List;

public abstract class JointController {

	protected List<Joint> joints;

	public JointController() {
		this(new ArrayList<>());
	}

	public JointController(List<Joint> joints) {
		this.joints = joints;
	}

	public List<Joint> getJoints() {
		return joints;
	}

	public final void updateJoints() {
		doUpdateJoints(joints);
	}

	public abstract void doUpdateJoints(List<Joint> joints);

}
