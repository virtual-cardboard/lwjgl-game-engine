package context.visuals.gui;

import static context.visuals.renderer.TextRenderer.ALIGN_LEFT;

import common.math.Matrix4f;
import common.math.Vector2f;
import context.GLContext;
import context.visuals.builtin.RectangleRenderer;
import context.visuals.renderer.TextRenderer;
import context.visuals.text.GameFont;

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
	public void render(GLContext glContext, Vector2f screenDim, float x, float y, float width, float height) {
		rectangleRenderer.render(glContext, screenDim, x, y, width, height, colour);
		textRenderer.setAlign(align);
		textRenderer.render(glContext, screenDim, new Matrix4f().translate(x + paddingLeft, y + paddingTop), text,
				width - paddingLeft - paddingRight, font, size, textColour);
	}

}