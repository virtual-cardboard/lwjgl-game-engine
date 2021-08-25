package context.data.animation;

import common.math.Vector2f;

public class Joint {

	private Vector2f position;
	private float rotation;

	public Joint() {
		position = new Vector2f();
	}

	public Vector2f getPosition() {
		return position;
	}

	public float getRotation() {
		return rotation;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

}
