package common.physics;

public class OctalCompass {

//	private static final float SQRT_TWO_OVER_TWO = (float) (Math.sqrt(2) / 2);

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

	public void center() {
		x = 0;
		y = 0;
	}

}
