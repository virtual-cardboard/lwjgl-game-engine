package context.data.animation;

public class SkeletonNodeState {

	private float distance;
	private float rotation;

	public SkeletonNodeState() {
	}

	public SkeletonNodeState(float distance, float rotation) {
		this.distance = distance;
		this.rotation = rotation;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public float getRotation() {
		return rotation;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

}
