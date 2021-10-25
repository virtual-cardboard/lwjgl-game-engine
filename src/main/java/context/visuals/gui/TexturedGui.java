package context.visuals.gui;

import common.math.Matrix4f;
import context.visuals.lwjgl.Texture;
import context.visuals.renderer.TextureRenderer;

public class TexturedGui extends Gui {

	private Texture texture;
	private TextureRenderer textureRenderer;

	public TexturedGui(TextureRenderer textureRenderer, Texture texture) {
		this.textureRenderer = textureRenderer;
		this.texture = texture;
	}

	@Override
	public void render(Matrix4f matrix4f, float x, float y, float width, float height) {
		textureRenderer.render(texture, matrix4f.translate(x, y).scale(width, height));
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

}
