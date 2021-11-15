package context.visuals.gui;

import common.math.Vector2f;
import context.GLContext;
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
	public void render(GLContext glContext, Vector2f screenDim, float x, float y, float width, float height) {
		textureRenderer.render(glContext, texture, rectToPixelMatrix4f(screenDim).translate(x, y).scale(width, height));
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

}
