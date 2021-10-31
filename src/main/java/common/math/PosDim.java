package common.math;

public class PosDim {

	public final float x;
	public final float y;
	public final float w;
	public final float h;

	public PosDim(float x, float y, float w, float h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public Vector2f pos() {
		return new Vector2f(x, y);
	}

	public Vector2f dim() {
		return new Vector2f(w, h);
	}

	public float x() {
		return x;
	}

	public float y() {
		return y;
	}

	public float w() {
		return w;
	}

	public float h() {
		return h;
	}

}
