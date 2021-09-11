package context.data.animation.skeleton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SkeletonNode {

	private int childIndex = -1;
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
		child.childIndex = children.size();
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

	public SkeletonNode getNthNode(int n) {
		return getNthNodeRecursive(new AtomicInteger(n));
	}

	private SkeletonNode getNthNodeRecursive(AtomicInteger atomicInteger) {
		if (atomicInteger.get() == 0) {
			return this;
		}
		atomicInteger.set(atomicInteger.get() - 1);
		for (SkeletonNode child : getChildren()) {
			SkeletonNode getNth = child.getNthNodeRecursive(atomicInteger);
			if (getNth != null) {
				return getNth;
			}
		}
		return null;
	}

	public int getChildIndex() {
		return childIndex;
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
		for (int i = 0; i < children.size(); i++) {
			copy.addChild(children.get(i).deepCopy());
		}
		return copy;
	}

}
