package context.visuals.gui;

import common.math.Matrix4f;
import context.visuals.lwjgl.Texture;
import context.visuals.renderer.TextureRenderer;

public class TexturedGui extends Gui {

	private Texture texture;
	private TextureRenderer textureRenderer;

	public TexturedGui(Texture texture, TextureRenderer textureRenderer) {
		this.texture = texture;
		this.textureRenderer = textureRenderer;
	}

	@Override
	public void additionalRenderActions(Matrix4f matrix4f) {
		textureRenderer.render(texture, matrix4f);
	}

	public Texture getTexture() {
		return texture;
	}

}
