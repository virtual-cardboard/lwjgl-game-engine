package common.math;

import java.nio.FloatBuffer;
import java.nio.LongBuffer;

public class Vector2l extends Vector {

	private static final long serialVersionUID = 3362276223716947671L;

	public long x;
	public long y;

	public Vector2l() {
	}

	public Vector2l(long x, long y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public float lengthSquared() {
		return x * x + y * y;
	}

	@Override
	public Vector2l load(FloatBuffer buf) {
		x = (long) buf.get();
		y = (long) buf.get();
		return this;
	}

	public Vector2l load(LongBuffer buf) {
		x = buf.get();
		y = buf.get();
		return this;
	}

	@Override
	public Vector2l negate() {
		x = -x;
		y = -y;
		return this;
	}

	public Vector2l add(Vector2l vector) {
		x += vector.x;
		y += vector.y;
		return this;
	}

	public Vector2l add(long x, long y) {
		this.x += x;
		this.y += y;
		return this;
	}

	public Vector2l sub(Vector2l vector) {
		x -= vector.x;
		y -= vector.y;
		return this;
	}

	@Override
	public Vector2l store(FloatBuffer buf) {
		buf.put(x);
		buf.put(y);
		return this;
	}

	public Vector2l store(LongBuffer buf) {
		buf.put(x);
		buf.put(y);
		return this;
	}

	@Override
	public Vector2l scale(float scale) {
		x *= scale;
		y *= scale;
		return this;
	}

	public Vector2l set(long x, long y) {
		this.x = x;
		this.y = y;
		return this;
	}

	@Override
	public Vector2l copy() {
		return new Vector2l(x, y);
	}

	public Vector2l add(Vector2f vector2f) {
		x += vector2f.x;
		y += vector2f.y;
		return this;
	}

	public Vector2l sub(Vector2f vector2f) {
		x -= vector2f.x;
		y -= vector2f.y;
		return this;
	}

	public Vector2l sub(long x, long y) {
		this.x -= x;
		this.y -= y;
		return this;
	}

	public Vector2f toVec2f() {
		return new Vector2f(x, y);
	}

}
