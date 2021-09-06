package context.data.animation.skeleton;

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

}
