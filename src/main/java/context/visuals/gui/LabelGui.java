package context.visuals.gui;

import common.math.Matrix4f;
import context.GLContext;
import context.visuals.renderer.TextRenderer;
import context.visuals.text.GameFont;

public class LabelGui extends Gui {

	private TextRenderer textRenderer;
	private String text;
	private GameFont font;
	private int colour;
	private float size;

	public LabelGui(TextRenderer textRenderer, String text, GameFont font, int colour, float size) {
		this.textRenderer = textRenderer;
		this.text = text;
		this.font = font;
		this.colour = colour;
		this.size = size;
	}

	@Override
	public void render(GLContext glContext, Matrix4f matrix4f, float x, float y, float width, float height) {
		textRenderer.render(glContext, matrix4f, text, x, y, width, font, size, colour);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
