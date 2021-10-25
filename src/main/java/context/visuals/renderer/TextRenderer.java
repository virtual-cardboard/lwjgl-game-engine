package context.visuals.renderer;

import common.math.Matrix4f;
import common.math.Vector2f;
import common.math.Vector4f;
import context.GameContext;
import context.ResourcePack;
import context.visuals.colour.Colour;
import context.visuals.gui.RootGui;
import context.visuals.lwjgl.ShaderProgram;
import context.visuals.text.CharacterData;
import context.visuals.text.GameFont;

/**
 * A {@link GameRenderer} that renders text.
 * 
 * @author Jay
 *
 */
public class TextRenderer extends GameRenderer {

	/**
	 * The {@link ShaderProgram} to use when rendering text.
	 */
	private ShaderProgram shaderProgram;

	/**
	 * Creates a TextRenderer using the text shader program from a
	 * {@link GameContext}'s {@link ResourcePack}. The text shader program should be
	 * put into the <code>ResourcePack</code> with the name <code>"text"</code>.
	 * 
	 * @param context the <code>GameContext</code>
	 */
	public TextRenderer(GameContext context) {
		super(context);
		shaderProgram = resourcePack.getShaderProgram("text");
	}

	/**
	 * Renders text.
	 * 
	 * @param rootGui   the {@link RootGui}
	 * @param text      the {@link String} to display
	 * @param x         the <code>x</code> offset of the text from the left side of
	 *                  the screen
	 * @param y         the <code>y</code> offset of the text from the top of the
	 *                  screen
	 * @param lineWidth the max width of a line of text in pixels, or -1 to indicate
	 *                  no wrapping of text
	 * @param font      the {@link GameFont} of the text
	 * @param fontSize  the size of the text
	 * @param colour    the colour of the text
	 */
	public void render(RootGui rootGui, String text, int x, int y, int lineWidth, GameFont font, float fontSize, int colour) {
		Vector2f rootGuiDimensions = rootGui.getDimensions();
		Matrix4f matrix4f = new Matrix4f();
		matrix4f.translate(-1, 1).scale(2, -2).scale(1 / rootGuiDimensions.x, 1 / rootGuiDimensions.y);
		render(matrix4f, text, x, y, lineWidth, font, fontSize, colour);
	}

	public void render(Matrix4f matrix4f, String text, float x, float y, float lineWidth, GameFont font, float fontSize, int colour) {
		char[] chars = text.toCharArray();
		int totalXOffset = 0;
		int totalYOffset = 0;

		shaderProgram.bind();
		font.texture().bind(glContext);

		matrix4f.translate(x, y);
		float sizeMultiplier = fontSize / font.getFontSize();
		Vector4f colourVec4 = Colour.toNormalizedVector(colour);
		for (int i = 0; i < chars.length; i++) {
			CharacterData c = font.getCharacterDatas()[chars[i]];
			short xAdvance = c.xAdvance();
			if (lineWidth != -1 && totalXOffset + xAdvance > lineWidth) {
				totalXOffset = 0;
				totalYOffset += fontSize;
			}
			shaderProgram.setInt("textureSampler", font.texture().getTextureUnit());
			shaderProgram.setFloat("texWidth", font.texture().getWidth());
			shaderProgram.setFloat("texHeight", font.texture().getHeight());
			shaderProgram.setVec4("colour", colourVec4);
			displayChar(matrix4f.clone(), c, totalXOffset, totalYOffset, sizeMultiplier);
			totalXOffset += xAdvance * sizeMultiplier;
		}
	}

	private void displayChar(Matrix4f matrix4f, CharacterData c, int totalXOffset, int totalYOffset, float sizeMultiplier) {
		matrix4f.translate(totalXOffset + c.xOffset() * sizeMultiplier, totalYOffset + c.yOffset() * sizeMultiplier);
		matrix4f.scale(c.width() * sizeMultiplier, c.height() * sizeMultiplier);
		shaderProgram.setMat4("matrix4f", matrix4f);
		shaderProgram.setFloat("width", c.width());
		shaderProgram.setFloat("height", c.height());
		shaderProgram.setFloat("x", c.x());
		shaderProgram.setFloat("y", c.y());
		resourcePack.rectangleVAO().display(glContext);
	}

}
