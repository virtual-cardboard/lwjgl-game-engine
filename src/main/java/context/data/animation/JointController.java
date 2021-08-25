package context.data.animation;

import java.util.ArrayList;
import java.util.List;

public abstract class JointController {

	private List<Joint> joints;

	public JointController() {
		joints = new ArrayList<>();
	}

	public final void updateJoints() {
		doUpdateJoints(joints);
	}

	public abstract void doUpdateJoints(List<Joint> joints);

}
