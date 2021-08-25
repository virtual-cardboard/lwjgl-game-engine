package context.data.animation;

import java.util.ArrayList;
import java.util.List;

public class SkeletonState {

	private List<SkeletonNodeState> skeletonNodeStates;

	public SkeletonState() {
		this.skeletonNodeStates = new ArrayList<>();
	}

	public List<SkeletonNodeState> getSkeletonNodeStates() {
		return skeletonNodeStates;
	}

	public void setSkeletonNodeStates(List<SkeletonNodeState> skeletonNodeStates) {
		this.skeletonNodeStates = skeletonNodeStates;
	}

}
