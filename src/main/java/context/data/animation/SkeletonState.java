package context.data.animation;

import java.util.ArrayList;
import java.util.List;

import common.math.Vector2f;

public class SkeletonState {

	private List<Float> rotations;
	private List<Float> distances;
	private Vector2f rootPosition;

	public SkeletonState() {
		this.rotations = new ArrayList<>();
		this.distances = new ArrayList<>();
		rootPosition = new Vector2f();
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

}
