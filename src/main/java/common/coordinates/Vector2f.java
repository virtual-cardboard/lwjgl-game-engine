package common.coordinates;

public class Vector2f extends AbstractCoordinates {

	public Vector2f(float x, float y) {
		super(x, y);
	}

	public static Vector2f fromXY(float x, float y) {
		return new Vector2f(x, y);
	}

	public static Vector2f fromLengthAngle(float length, float angle) {
		return new Vector2f((float) (length * Math.cos(angle)), (float) (length * Math.sin(angle)));
	}

	public float getLengthSquared() {
		return x * x + y * y;
	}

	public float getLength() {
		return (float) Math.sqrt(x * x + y * y);
	}

	public Vector2f add(Vector2f vector) {
		x += vector.x;
		y += vector.y;
		return this;
	}

	public Vector2f subtract(Vector2f vector) {
		x -= vector.x;
		y -= vector.y;
		return this;
	}

	public Vector2f scale(float factor) {
		x *= factor;
		y *= factor;
		return this;
	}

	/**
	 * Modifies the vector so that its length is 1.
	 * 
	 * @return the modified vector
	 */
	public Vector2f normalize() {
		return scale(1 / getLength());
	}

	public PixelCoordinates toPixelCoordinates() {
		return new PixelCoordinates(x, y);
	}

	public Vector2f copy() {
		return new Vector2f(x, y);
	}

}
