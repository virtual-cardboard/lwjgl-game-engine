package context.visuals.gui;

import context.visuals.gui.renderer.GuiRenderer;
import context.visuals.lwjgl.Texture;

public class TexturedGui extends Gui {

	private Texture texture;

	public TexturedGui(GuiRenderer<TexturedGui> guiRenderer, Texture texture) {
		super(guiRenderer);
		this.texture = texture;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

}
