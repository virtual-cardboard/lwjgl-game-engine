package context.data.animation.animation;

public class Keyframe implements Comparable<Keyframe> {

	private int time;
	private SkeletonState skeletonState;

	public Keyframe(int time, SkeletonState skeletonState) {
		this.time = time;
		this.skeletonState = skeletonState;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public SkeletonState getSkeletonState() {
		return skeletonState;
	}

	@Override
	public int compareTo(Keyframe o) {
		return Integer.compare(time, o.time);
	}

}
