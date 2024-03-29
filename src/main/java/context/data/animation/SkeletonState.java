package context.data.animation;

import java.util.ArrayList;
import java.util.List;

import context.data.animation.skeleton.Skeleton;
import context.data.animation.skeleton.SkeletonNode;
import engine.common.math.Vector2f;

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
		return getPositionOfRecursive(node).position;
	}

	private PositionRotationContainer getPositionOfRecursive(SkeletonNode node) {
		int index = node.getIndex();
		if (index == 0) {
			return new PositionRotationContainer(rootPosition, rotations.get(0));
		}
		PositionRotationContainer parentPosRot = getPositionOfRecursive(node.getParent());
		parentPosRot.rotation += rotations.get(index);
		parentPosRot.position = parentPosRot.position.add(Vector2f.fromAngleLength(parentPosRot.rotation, distances.get(index)));
		return parentPosRot;
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

	public void setRootPosition(Vector2f rootPosition) {
		this.rootPosition = rootPosition;
	}

	public SkeletonState copy() {
		SkeletonState copy = new SkeletonState();
		copy.rotations.addAll(rotations);
		copy.distances.addAll(distances);
		copy.rootPosition = rootPosition;
		return copy;
	}

	private static final class PositionRotationContainer {

		private Vector2f position;
		private float rotation;

		private PositionRotationContainer() {
			this(new Vector2f(0, 0), 0);
		}

		private PositionRotationContainer(Vector2f position, float rotation) {
			this.position = position;
			this.rotation = rotation;
		}

	}

}
