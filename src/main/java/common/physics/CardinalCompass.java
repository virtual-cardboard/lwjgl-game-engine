package common.physics;

import common.coordinates.VectorCoordinates;

public class CardinalCompass {

	private static final float SQRT_TWO_OVER_TWO = 0.70710678118f;

	private int x = 0;
	private int y = 0;

	public void north() {
		if (y != -1) {
			y--;
		}
	}

	public void east() {
		if (x != 1) {
			x++;
		}
	}

	public void south() {
		if (y != 1) {
			y++;
		}
	}

	public void west() {
		if (x != -1) {
			x--;
		}
	}

	public VectorCoordinates getDirection() {
		VectorCoordinates vector = new VectorCoordinates(x, y);
		if (Math.abs(x) + Math.abs(y) == 2) {
			vector.scale(SQRT_TWO_OVER_TWO);
		}
		return vector;
	}

}
