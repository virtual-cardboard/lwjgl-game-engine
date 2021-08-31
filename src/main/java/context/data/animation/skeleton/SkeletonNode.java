package context.data.animation.skeleton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SkeletonNode {

	private SkeletonNode parent;
	private List<SkeletonNode> children;

	public SkeletonNode() {
		children = new ArrayList<>();
	}

	public SkeletonNode(SkeletonNode... children) {
		this.children = new ArrayList<>(Arrays.asList(children));
	}

	public void addChild(SkeletonNode child) {
		children.add(child);
		child.parent = this;
	}

	public SkeletonNode getParent() {
		return parent;
	}

	public List<SkeletonNode> getChildren() {
		return children;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof SkeletonNode)) {
			return false;
		}
		SkeletonNode node = (SkeletonNode) obj;
		return node.getChildren().equals(children);
	}

}
