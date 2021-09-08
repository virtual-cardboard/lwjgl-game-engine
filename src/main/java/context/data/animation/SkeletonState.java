package context.data.animation;

import java.util.ArrayList;
import java.util.List;

import common.math.Vector2f;
import context.data.animation.skeleton.Skeleton;

public class SkeletonState {

	private List<Float> rotations;
	private List<Float> distances;
	private Vector2f rootPosition;

	public SkeletonState() {
		this.rotations = new ArrayList<>();
		this.distances = new ArrayList<>();
		rootPosition = new Vector2f();
	}

	public SkeletonState(Skeleton skeleton) {
		this();
		int totalNumNodes = skeleton.totalNumNodes();
		for (int i = 0; i < totalNumNodes; i++) {
			rotations.add(0f);
			distances.add(50f);
		}
	}

	public List<Float> getRotations() {
		return rotations;
	}

	public List<Float> getDistances() {
		return distances;
	}

	public Vector2f getRootPosition() {
		return rootPosition;
	}

	public SkeletonState copy() {
		SkeletonState copy = new SkeletonState();
		copy.getRootPosition().set(rootPosition);
		List<Float> rotationsCopy = copy.getRotations();
		List<Float> distancesCopy = copy.getDistances();
		for (int i = 0; i < rotations.size(); i++) {
			rotationsCopy.add(rotations.get(i));
			distancesCopy.add(distances.get(i));
		}
		return null;
	}

}
