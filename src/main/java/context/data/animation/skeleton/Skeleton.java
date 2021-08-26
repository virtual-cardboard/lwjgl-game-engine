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

}
