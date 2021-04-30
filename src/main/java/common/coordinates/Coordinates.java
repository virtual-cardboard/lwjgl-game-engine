package common.coordinates;

import java.io.Serializable;

public class Coordinates<T extends Number> implements Serializable {

	private static final long serialVersionUID = -3775082492525975448L;

	public T x;
	public T y;

	public Coordinates(T x, T y) {
		this.x = x;
		this.y = y;
	}

	public void set(T x, T y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}

}
