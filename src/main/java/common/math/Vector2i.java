package common.math;

import java.nio.FloatBuffer;
import java.util.Objects;

public class Vector2i extends Vector {

	private static final long serialVersionUID = 3362276223716947671L;

	public int x;
	public int y;

	public Vector2i() {
	}

	public Vector2i(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public float lengthSquared() {
		return x * x + y * y;
	}

	@Override
	public Vector2i load(FloatBuffer buf) {
		x = (int) buf.get();
		y = (int) buf.get();
		return this;
	}

	@Override
	public Vector2i negate() {
		x = -x;
		y = -y;
		return this;
	}

	public Vector2i add(Vector2i vector) {
		x += vector.x;
		y += vector.y;
		return this;
	}

	public Vector2i add(int x, int y) {
		this.x += x;
		this.y += y;
		return this;
	}

	public Vector2i sub(Vector2i vector) {
		x -= vector.x;
		y -= vector.y;
		return this;
	}

	@Override
	public Vector2i store(FloatBuffer buf) {
		buf.put(x);
		buf.put(y);
		return this;
	}

	@Override
	public Vector2i scale(float scale) {
		x *= scale;
		y *= scale;
		return this;
	}

	public Vector2i set(int x, int y) {
		this.x = x;
		this.y = y;
		return this;
	}

	public Vector2i set(Vector2i vector) {
		this.x = vector.x;
		this.y = vector.y;
		return this;
	}

	public Vector2f toVec2f() {
		return new Vector2f(x, y);
	}

	@Override
	public Vector2i copy() {
		return new Vector2i(x, y);
	}

	@Override
	public String toString() {
		return "Vector2i[" + x + ", " + y + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Vector2i other = (Vector2i) obj;
		if (x == other.x && y == other.y)
			return true;
		return false;
	}

}
