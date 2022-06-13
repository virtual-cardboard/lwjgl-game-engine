package context.visuals.gui;

import static context.visuals.renderer.TextRenderer.ALIGN_LEFT;

import context.GLContext;
import context.data.GameData;
import context.visuals.builtin.RectangleRenderer;
import context.visuals.renderer.TextRenderer;
import context.visuals.text.GameFont;
import engine.common.math.Matrix4f;

public class LabelGui extends Gui {

	private RectangleRenderer rectangleRenderer;
	private TextRenderer textRenderer;
	private GameFont font;
	private String text;
	private float size;
	private int textColour;
	private int colour;

	public int align = ALIGN_LEFT;

	public float paddingLeft;
	public float paddingRight;
	public float paddingTop;

	public LabelGui(RectangleRenderer rectangleRenderer, TextRenderer textRenderer, GameFont font, String text, float size, int textColour, int colour) {
		this.rectangleRenderer = rectangleRenderer;
		this.textRenderer = textRenderer;
		this.font = font;
		this.text = text;
		this.size = size;
		this.textColour = textColour;
		this.colour = colour;
	}

	@Override
	public void render(GLContext glContext, GameData data, float x, float y, float width, float height) {
		rectangleRenderer.render(x, y, width, height, colour);
		textRenderer.setHorizontalAlign(align);
		textRenderer.render(new Matrix4f().translate(x + paddingLeft, y + paddingTop), text, width - paddingLeft - paddingRight, font,
				size, textColour);
	}

}
