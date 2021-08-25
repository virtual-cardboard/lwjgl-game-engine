package context.data.animation.interpolation;

public class Angles {

	private Angles() {
	}

	public static float angleDifference(float angle1, float angle2) {
		double diff = (Math.PI + angle2 - angle1) % (2 * Math.PI) - Math.PI;
		return (float) (diff < -Math.PI ? diff + 2 * Math.PI : diff);
	}

}
