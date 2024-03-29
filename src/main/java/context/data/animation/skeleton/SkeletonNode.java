package context.data.animation.skeleton;

import java.util.ArrayList;
import java.util.List;

public class SkeletonNode {

	private int index = -1;
	private SkeletonNode parent;
	private List<SkeletonNode> children;

	public SkeletonNode() {
		children = new ArrayList<>();
	}

	public SkeletonNode(SkeletonNode... children) {
		this();
		for (int i = 0; i < children.length; i++) {
			addChild(children[i]);
		}
	}

	public void addChild(SkeletonNode child) {
		children.add(child);
		child.parent = this;
	}

	public int totalNumDescendants() {
		int num = 1;
		for (int i = 0; i < children.size(); i++) {
			num += children.get(i).totalNumDescendants();
		}
		return num;
	}

	public int getIndex() {
		return index;
	}

	void setIndex(int index) {
		this.index = index;
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

	public SkeletonNode deepCopy() {
		SkeletonNode copy = new SkeletonNode();
		copy.index = index;
		for (int i = 0; i < children.size(); i++) {
			copy.addChild(children.get(i).deepCopy());
		}
		return copy;
	}

}
