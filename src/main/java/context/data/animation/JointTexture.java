package context.data.animation;

import common.math.Vector2f;
import context.visuals.lwjgl.Texture;

public class JointTexture {

	private Texture texture;

	private Vector2f translation;
	private Vector2f scale;
	private float rotation;

	public JointTexture(Texture texture) {
		this.texture = texture;
		translation = new Vector2f();
		scale = new Vector2f();
		rotation = 0;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public Vector2f getTranslation() {
		return translation;
	}

	public Vector2f getScale() {
		return scale;
	}

	public float getRotation() {
		return rotation;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

}
