package context.data.animation;

import java.util.ArrayList;
import java.util.List;

import common.math.Vector2f;
import context.data.animation.skeleton.Skeleton;
import context.data.animation.skeleton.SkeletonNode;

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

	public float getAbsoluteRotationOf(SkeletonNode node) {
		if (node.getIndex() == 0) {
			return rotations.get(0);
		}
		return getAbsoluteRotationOf(node.getParent()) + rotations.get(node.getIndex());
	}

	public Vector2f getPositionOf(SkeletonNode node) {
		int index = node.getIndex();
		if (index == 0) {
			return rootPosition;
		}
		return Vector2f.fromAngleLength(rotations.get(index), distances.get(index)).add(getPositionOf(node.getParent()));
	}

//	public float getAbsoluteRotationOf(Skeleton skeleton, int nodeIndex) {
//		return getAbsoluteRotationOfRecursive(skeleton.getNthNode(nodeIndex), nodeIndex);
//	}
//
//	private float getAbsoluteRotationOfRecursive(SkeletonNode node, int nodeIndex) {
//		if (nodeIndex == 0) {
//			return rotations.get(0);
//		}
//		SkeletonNode parent = node.getParent();
//		float relativeRot = rotations.get(nodeIndex);
//		int parentIndex = nodeIndex - node.getIndex() - 1;
//		return relativeRot + getAbsoluteRotationOfRecursive(parent, parentIndex);
//	}

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
		copy.rotations.addAll(rotations);
		copy.distances.addAll(distances);
		copy.rootPosition.set(rootPosition);
		return copy;
	}

}
