package context.data.animation.skeleton;

import java.util.List;

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
		return doTotalNumNodes(rootNode);
	}

	private int doTotalNumNodes(SkeletonNode node) {
		int num = 1;
		List<SkeletonNode> children = node.getChildren();
		for (int i = 0; i < children.size(); i++) {
			SkeletonNode child = children.get(i);
			num += doTotalNumNodes(child);
		}
		return num;
	}

}
