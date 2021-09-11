package context.data.animation.skeleton;

import context.data.animation.SkeletonState;

public class Skeleton {

	private SkeletonNode rootNode;

	public Skeleton() {
		this(new SkeletonNode());
	}

	public Skeleton(SkeletonNode rootNode) {
		this.rootNode = rootNode;
	}

	public SkeletonNode getRootNode() {
		return rootNode;
	}

	public int totalNumNodes() {
		return rootNode.totalNumDescendants();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Skeleton)) {
			return false;
		}
		Skeleton skeleton = (Skeleton) obj;
		return rootNode.equals(skeleton.getRootNode());
	}

	public Skeleton deepCopy() {
		return new Skeleton(rootNode.deepCopy());
	}

	public float getAbsoluteRotation(SkeletonState state, int nodeIndex) {
		return getAbsoluteRotationRecursive(rootNode.getNthNode(nodeIndex), state, nodeIndex);
	}

	private float getAbsoluteRotationRecursive(SkeletonNode node, SkeletonState state, int nodeIndex) {
		if (nodeIndex == 0) {
			return state.getRotations().get(0);
		}
		SkeletonNode parent = node.getParent();
		float relativeRot = state.getRotations().get(nodeIndex);
		int parentIndex = nodeIndex - node.getChildIndex() - 1;
		return relativeRot + getAbsoluteRotationRecursive(parent, state, parentIndex);
	}

}
