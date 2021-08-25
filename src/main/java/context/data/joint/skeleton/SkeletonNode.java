package context.data.joint.skeleton;

import java.util.ArrayList;
import java.util.List;

public class SkeletonNode {

	private SkeletonNode parent;
	private List<SkeletonNode> children;

	public SkeletonNode() {
		children = new ArrayList<>();
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

}
