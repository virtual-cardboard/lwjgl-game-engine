package context.data.animation;

import java.util.ArrayList;
import java.util.List;

import common.math.Vector2f;

public class SkeletonState {

	private List<SkeletonNodeState> skeletonNodeStates;
	private Vector2f rootPosition;

	public SkeletonState() {
		this.skeletonNodeStates = new ArrayList<>();
		rootPosition = new Vector2f();
	}

	public List<SkeletonNodeState> getSkeletonNodeStates() {
		return skeletonNodeStates;
	}

	public Vector2f getRootPosition() {
		return rootPosition;
	}

}
