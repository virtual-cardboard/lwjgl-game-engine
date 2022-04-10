package context.data.animation.joint;

import engine.common.math.Vector2f;

public class Joint {

	private Vector2f position;
	private float rotation;

	public Joint() {
		position = new Vector2f();
	}

	public Vector2f position() {
		return position;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public float rotation() {
		return rotation;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

}
