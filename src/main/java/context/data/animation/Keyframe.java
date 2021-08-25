package context.data.animation;

public class Keyframe implements Comparable<Keyframe> {

	private int time;
	private SkeletonState skeletonState;
	private InterpolationType interpolationType;

	public Keyframe(int time, SkeletonState skeletonState) {
		this.time = time;
		this.skeletonState = skeletonState;
		interpolationType = InterpolationType.LINEAR;
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

	public InterpolationType getInterpolationType() {
		return interpolationType;
	}

	public void setInterpolationType(InterpolationType interpolationType) {
		this.interpolationType = interpolationType;
	}

	@Override
	public int compareTo(Keyframe o) {
		return Integer.compare(time, o.time);
	}

}
