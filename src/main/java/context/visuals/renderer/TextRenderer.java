package context.visuals.renderer;

import static context.visuals.defaultvao.RectangleVertexArrayObject.getRectangleVAO;

import java.util.Objects;

import common.math.Matrix4f;
import common.math.Vector2f;
import context.visuals.gui.RootGui;
import context.visuals.lwjgl.ShaderProgram;
import context.visuals.lwjgl.Texture;
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
	 * Creates a TextRenderer with an text {@link ShaderProgram}.
	 * 
	 * @param textShaderProgram the text shader program
	 */
	public TextRenderer(ShaderProgram textShaderProgram) {
		Objects.requireNonNull(textShaderProgram);
		shaderProgram = textShaderProgram;
	}

	/**
	 * Renders text.
	 * 
	 * @param rootGui  the {@link RootGui}
	 * @param text     the {@link String} to display
	 * @param x        the <code>x</code> offset of the text from the left side of
	 *                 the screen
	 * @param y        the <code>y</code> offset of the text from the top of the
	 *                 screen
	 * @param width    the width of the text, or -1 to indicate no wrapping of text
	 * @param font     the {@link GameFont} of the text
	 * @param fontSize the size of the text
	 */
	public void render(RootGui rootGui, String text, int x, int y, int width, GameFont font, int fontSize) {
		char[] chars = text.toCharArray();
		int totalXOffset = 0;
		int totalYOffset = 0;

		shaderProgram.bind();
		Texture t = font.texture();
		shaderProgram.setInt("textureSampler", t.getTextureUnit());
		shaderProgram.setFloat("texWidth", t.getWidth());
		shaderProgram.setFloat("texHeight", t.getHeight());

		Vector2f rootGuiDimensions = rootGui.getDimensions();
		Matrix4f matrix4f = new Matrix4f();
		matrix4f.translate(-1, 1).scale(2, -2).scale(1 / rootGuiDimensions.x, 1 / rootGuiDimensions.y);
		matrix4f.translate(x, y);
		for (int i = 0; i < chars.length; i++) {
			CharacterData c = font.getCharacterDatas()[i];
			short xAdvance = c.xAdvance();
			if (width != -1 && totalXOffset + xAdvance > width) {
				totalXOffset = 0;
				totalYOffset += fontSize;
			}
			displayChar(matrix4f.clone(), c, totalXOffset, totalYOffset);
			totalXOffset += xAdvance;
		}
	}

	private void displayChar(Matrix4f matrix4f, CharacterData c, int totalXOffset, int totalYOffset) {
		matrix4f.translate(totalXOffset + c.xOffset(), totalYOffset + c.yOffset());
		shaderProgram.setMat4("matrix4f", matrix4f);
		shaderProgram.setFloat("width", c.width());
		shaderProgram.setFloat("height", c.height());
		shaderProgram.setFloat("x", c.x());
		shaderProgram.setFloat("y", c.y());
		getRectangleVAO().display();
	}

	public static void main(String[] args) {
//		Matrix4f matrix4f = new Matrix4f();
//		System.out.println(matrix4f.transform(0, 0));
//		System.out.println(matrix4f.transform(1, 1));
//		matrix4f.scale(0.001f, 0.01f);
//		System.out.println();
//		System.out.println(matrix4f.transform(0, 0));
//		System.out.println(matrix4f.transform(1, 1));
//		matrix4f.translate(65, 78);
//		System.out.println();
//		System.out.println(matrix4f.transform(0, 0));
//		System.out.println(matrix4f.transform(1, 1));
		System.out.println(8 % (-3));
	}

}
