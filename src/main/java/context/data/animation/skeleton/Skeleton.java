package context.data.animation.skeleton;

import engine.common.IntWrapper;

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

	void setRootNode(SkeletonNode rootNode) {
		this.rootNode = rootNode;
	}

	public int totalNumNodes() {
		return rootNode.totalNumDescendants();
	}

	public SkeletonNode getNthNode(int n) {
		return getNthNodeRecursive(rootNode, new IntWrapper(n));
	}

	private SkeletonNode getNthNodeRecursive(SkeletonNode node, IntWrapper i) {
		if (i.val == 0) {
			return node;
		}
		i.val--;
		for (SkeletonNode child : node.getChildren()) {
			SkeletonNode getNth = getNthNodeRecursive(child, i);
			if (getNth != null) {
				return getNth;
			}
		}
		return null;
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

}
