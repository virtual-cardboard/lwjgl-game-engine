package context.data.animation.skeleton;

import java.util.List;

import common.IntWrapper;

public class SkeletonBuilder {

	public Skeleton build(SkeletonNode rootNode) {
		markIndexes(rootNode, new IntWrapper(0));
		return new Skeleton(rootNode);
	}

	private void markIndexes(SkeletonNode node, IntWrapper intWrapper) {
		node.setIndex(intWrapper.val++);
		List<SkeletonNode> children = node.getChildren();
		for (int i = 0; i < children.size(); i++) {
			SkeletonNode child = children.get(i);
			markIndexes(child, intWrapper);
		}
	}

}