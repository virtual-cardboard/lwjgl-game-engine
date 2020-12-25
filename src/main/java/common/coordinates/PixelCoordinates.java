package common.coordinates;

public class PixelCoordinates extends AbstractCoordinates {

	public PixelCoordinates(float x, float y) {
		super(x, y);
	}

	public Vector2f toVector() {
		return new Vector2f(x, y);
	}

}
