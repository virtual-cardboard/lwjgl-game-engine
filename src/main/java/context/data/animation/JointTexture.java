package context.data.animation;

import common.math.Vector2f;
import context.visuals.lwjgl.Texture;

public class JointTexture {

	private Texture texture;
	private Vector2f translation;
	private Vector2f scale;
	private float rotation;

	public JointTexture(Texture texture) {
		this(texture, new Vector2f(-texture.width() / 2, -texture.height() / 2), new Vector2f(1, 1), 0);
	}

	public JointTexture(Texture texture, Vector2f translation, Vector2f scale, float rotation) {
		this.texture = texture;
		this.translation = translation;
		this.scale = scale;
		this.rotation = rotation;
	}

	public Texture texture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public Vector2f translation() {
		return translation;
	}

	public void setTranslation(Vector2f translation) {
		this.translation = translation;
	}

	public Vector2f scale() {
		return scale;
	}

	public void setScale(Vector2f scale) {
		this.scale = scale;
	}

	public float rotation() {
		return rotation;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj instanceof JointTexture) {
			JointTexture o = (JointTexture) obj;
			return texture.equals(o.texture) && translation.equals(o.translation) && scale.equals(o.scale) && rotation == o.rotation;
		}
		return false;
	}

	public JointTexture copy() {
		return new JointTexture(texture, translation, scale, rotation);
	}

}
