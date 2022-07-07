package engine.common.math;

import static engine.common.math.MathSerializationFormats.VECTOR_3L;

import java.util.Objects;

import derealizer.SerializationReader;
import derealizer.SerializationWriter;
import derealizer.format.Serializable;

public class Vector3l implements Serializable {

	long x;
	long y;
	long z;

	public Vector3l() {
	}

	public Vector3l(long x, long y, long z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3l(Vector3l src) {
		this.x = src.x;
		this.y = src.y;
		this.z = src.z;
	}

	public Vector3l(byte[] bytes) {
		read(new SerializationReader(bytes));
	}

	public Vector3l negate() {
		return new Vector3l(-x, -y, -z);
	}

	public Vector3l add(Vector3l vector) {
		return new Vector3l(x + vector.x, y + vector.y, z + vector.z);
	}

	public Vector3l sub(Vector3l vector) {
		return new Vector3l(x - vector.x, y - vector.y, z - vector.z);
	}

	public Vector3l scale(float scale) {
		return new Vector3l((long) (x * scale), (long) (y * scale), (long) (z * scale));
	}

	public Vector3l normalise() {
		float len = length();
		if (len != 0f) {
			return scale(1f / len);
		} else {
			throw new IllegalStateException("Zero length vector");
		}
	}

	public long lengthSquared() {
		return x * x + y * y + z * z;
	}

	public long length() {
		return (long) Math.sqrt(lengthSquared());
	}

	public float dot(Vector3l vector) {
		return x * vector.x + y * vector.y + z * vector.z;
	}

	public long x() {
		return x;
	}

	public long y() {
		return y;
	}

	public long z() {
		return z;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y, z);
	}

	@Override
	public String toString() {
		return "Vector3l[" + x + ", " + y + ", " + z + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Vector3l other = (Vector3l) obj;
		return x == other.x && y == other.y && z == other.z;
	}

	@Override
	public MathSerializationFormats formatEnum() {
		return VECTOR_3L;
	}

	@Override
	public void read(SerializationReader reader) {
		this.x = reader.readLong();
		this.y = reader.readLong();
		this.z = reader.readLong();
	}

	@Override
	public void write(SerializationWriter writer) {
		writer.consume(x);
		writer.consume(y);
		writer.consume(z);
	}

}
