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
	 * @param x         the <code>x</code> offset of the text from the left side of
	 *                  the screen
	 * @param y         the <code>y</code> offset of the text from the top of the
	 *                  screen
	 * @param text      the {@link String} to display
	 * @param lineWidth the max width of a line of text in pixels, or -1 to indicate
	 *                  no wrapping of text
	 * @param font      the {@link GameFont} of the text
	 * @param fontSize  the size of the text
	 * @param colour    the colour of the text
	 */
	public void render(GLContext glContext, RootGui rootGui, int x, int y, String text, int lineWidth, GameFont font, float fontSize, int colour) {
		render(glContext, rootGui.dimensions(), new Matrix4f().translate(x, y), text, lineWidth, font, fontSize, colour);
	}

	public void render(GLContext glContext, Vector2f screenDim, Matrix4f transform, String text, float lineWidth, GameFont font, float fontSize, int colour) {
		char[] chars = text.toCharArray();
		int totalXOffset = 0;
		int totalYOffset = 0;

		shaderProgram.bind(glContext);
		shaderProgram.setInt("textureSampler", font.texture().getTextureUnit());
		shaderProgram.setFloat("texWidth", font.texture().width());
		shaderProgram.setFloat("texHeight", font.texture().height());
		shaderProgram.setColour("fill", colour);
		font.texture().bind(glContext);

		Matrix4f pixelScaleTransform = new Matrix4f().translate(-1, 1).scale(2, -2).scale(1 / screenDim.x, 1 / screenDim.y).multiply(transform);
		float sizeMultiplier = fontSize / font.getFontSize();
		for (int i = 0; i < chars.length; i++) {
			CharacterData c = font.getCharacterDatas()[chars[i]];
			if (chars[i] == ' ') {
				totalXOffset += c.xAdvance() * sizeMultiplier;
				continue;
			}
			short xAdvance = c.xAdvance();
			if (lineWidth > 0 && totalXOffset + xAdvance * sizeMultiplier > lineWidth) {
				totalXOffset = 0;
				totalYOffset += fontSize;
			}
			Matrix4f copy = pixelScaleTransform.copy()
					.translate(totalXOffset + c.xOffset() * sizeMultiplier, totalYOffset + c.yOffset() * sizeMultiplier)
					.scale(c.width() * sizeMultiplier, c.height() * sizeMultiplier);
			shaderProgram.setMat4("matrix4f", copy);
			shaderProgram.setFloat("width", c.width());
			shaderProgram.setFloat("height", c.height());
			shaderProgram.setFloat("x", c.x());
			shaderProgram.setFloat("y", c.y());
			vao.draw(glContext);
			totalXOffset += xAdvance * sizeMultiplier;
		}
	}

}
