package context.data.animation.interpolation;

import static java.lang.Math.PI;

public class Angles {

	private Angles() {
	}

	public static float angleDifference(float angle1, float angle2) {
		double diff = (PI + angle2 - angle1) % (2 * PI) - PI;
		return (float) (diff < -PI ? diff + 2 * PI : diff);
	}

	public static void main(String[] args) {
		System.out.println(angleDifference(0, 1));
		System.out.println(angleDifference(0, 3));
		System.out.println(angleDifference(0, 4));
		System.out.println(angleDifference(1.24532f, 4.389f));
	}

}
