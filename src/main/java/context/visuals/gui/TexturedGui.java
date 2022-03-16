package context.visuals.gui;

import context.GLContext;
import context.data.GameData;
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
	public void render(GLContext glContext, GameData data, float x, float y, float width, float height) {
		textureRenderer.render(texture, rectToPixelMatrix4f(glContext.windowDim()).translate(x, y).scale(width, height));
	}

	public Texture texture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

}
