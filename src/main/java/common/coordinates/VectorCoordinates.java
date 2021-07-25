package common.coordinates;

import java.io.Serializable;

public class VectorCoordinates extends FloatCoordinates implements Serializable {

	private static final long serialVersionUID = 7984461708642910479L;

	public VectorCoordinates(float x, float y) {
		super(x, y);
	}

	public static VectorCoordinates fromXY(float x, float y) {
		return new VectorCoordinates(x, y);
	}

	public static VectorCoordinates fromLengthAngle(float length, float angle) {
		return new VectorCoordinates((float) (length * Math.cos(angle)), (float) (length * Math.sin(angle)));
	}

	public float getLengthSquared() {
		return x * x + y * y;
	}

	public float getLength() {
		return (float) Math.sqrt(x * x + y * y);
	}

	public VectorCoordinates add(VectorCoordinates vector) {
		x += vector.x;
		y += vector.y;
		return this;
	}

	public VectorCoordinates subtract(VectorCoordinates vector) {
		x -= vector.x;
		y -= vector.y;
		return this;
	}

	public VectorCoordinates scale(float factor) {
		x *= factor;
		y *= factor;
		return this;
	}

	public VectorCoordinates normalize() {
		return scale(1 / getLength());
	}

	@Override
	public VectorCoordinates copy() {
		return new VectorCoordinates(x, y);
	}

}
