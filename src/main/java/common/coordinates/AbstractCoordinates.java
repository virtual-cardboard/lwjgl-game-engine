package common.coordinates;

public abstract class AbstractCoordinates {

	public float x;
	public float y;

	public AbstractCoordinates(float x, float y) {
		super();
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}

}
