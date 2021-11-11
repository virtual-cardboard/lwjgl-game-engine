package context.visuals.renderer;

import common.math.Matrix4f;
import common.math.Vector2f;
import context.GLContext;
import context.visuals.builtin.RectangleVertexArrayObject;
import context.visuals.builtin.TextShaderProgram;
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
	private RectangleVertexArrayObject vao;

	/**
	 * Creates a TextRenderer using a {@link TextShaderProgram}.
	 * 
	 * @param shaderProgram the {@link TextShaderProgram}
	 */
	public TextRenderer(TextShaderProgram shaderProgram, RectangleVertexArrayObject vao) {
		this.shaderProgram = shaderProgram;
		this.vao = vao;
	}

	/**
	 * Renders text.
	 * 
	 * @param glContext the {@link GLContext}
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
	public void render(GLContext glContext, RootGui rootGui, String text, int x, int y, int lineWidth, GameFont font, float fontSize, int colour) {
		Vector2f rootGuiDimensions = rootGui.getDimensions();
		Matrix4f matrix4f = new Matrix4f();
		matrix4f.translate(-1, 1).scale(2, -2).scale(1 / rootGuiDimensions.x, 1 / rootGuiDimensions.y);
		render(glContext, matrix4f, text, x, y, lineWidth, font, fontSize, colour);
	}

	public void render(GLContext glContext, Matrix4f matrix4f, String text, float x, float y, float lineWidth, GameFont font, float fontSize, int colour) {
		char[] chars = text.toCharArray();
		int totalXOffset = 0;
		int totalYOffset = 0;

		shaderProgram.bind();
		font.texture().bind(glContext);

		Matrix4f copy = matrix4f.copy().translate(x, y);
		float sizeMultiplier = fontSize / font.getFontSize();
		for (int i = 0; i < chars.length; i++) {
			CharacterData c = font.getCharacterDatas()[chars[i]];
			short xAdvance = c.xAdvance();
			if (lineWidth != -1 && totalXOffset + xAdvance * sizeMultiplier > lineWidth) {
				totalXOffset = 0;
				totalYOffset += fontSize;
			}
			shaderProgram.setInt("textureSampler", font.texture().getTextureUnit());
			shaderProgram.setFloat("texWidth", font.texture().width());
			shaderProgram.setFloat("texHeight", font.texture().height());
			shaderProgram.setColour("fill", colour);
			displayChar(glContext, copy.copy(), c, totalXOffset, totalYOffset, sizeMultiplier);
			totalXOffset += xAdvance * sizeMultiplier;
		}
	}

	private void displayChar(GLContext glContext, Matrix4f matrix4f, CharacterData c, int totalXOffset, int totalYOffset, float sizeMultiplier) {
		matrix4f.translate(totalXOffset + c.xOffset() * sizeMultiplier, totalYOffset + c.yOffset() * sizeMultiplier);
		matrix4f.scale(c.width() * sizeMultiplier, c.height() * sizeMultiplier);
		shaderProgram.setMat4("matrix4f", matrix4f);
		shaderProgram.setFloat("width", c.width());
		shaderProgram.setFloat("height", c.height());
		shaderProgram.setFloat("x", c.x());
		shaderProgram.setFloat("y", c.y());
		vao.draw(glContext);
	}

}
