package context.visuals.renderer;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

import java.util.ArrayList;
import java.util.List;

import context.visuals.builtin.RectangleVertexArrayObject;
import context.visuals.builtin.TextShaderProgram;
import context.visuals.colour.Colour;
import context.visuals.lwjgl.FrameBufferObject;
import context.visuals.lwjgl.ShaderProgram;
import context.visuals.lwjgl.Texture;
import context.visuals.text.CharacterData;
import context.visuals.text.GameFont;
import engine.common.Pair;
import engine.common.math.Matrix4f;

/**
 * A {@link GameRenderer} that renders text.
 *
 * @author Jay
 */
public class TextRenderer extends GameRenderer {

	public static final int ALIGN_LEFT = 0;
	public static final int ALIGN_RIGHT = 1;
	public static final int ALIGN_TOP = 2;
	public static final int ALIGN_BOTTOM = 3;
	public static final int ALIGN_CENTER = 4;

	private final TextureRenderer textureRenderer;
	/**
	 * The {@link ShaderProgram} to use when rendering text.
	 */
	private final ShaderProgram shaderProgram;
	private final RectangleVertexArrayObject vao;
	private final FrameBufferObject fbo;

	private int hAlign = ALIGN_LEFT;
	private int vAlign = ALIGN_TOP;

	/**
	 * Creates a TextRenderer using a {@link TextShaderProgram}.
	 *
	 * @param shaderProgram the <code>TextShaderProgram</code>
	 * @param vao           the <code>RectangleVertexArrayObject</code>
	 * @param fbo           the <code>FrameBufferObject</code>
	 */
	public TextRenderer(TextureRenderer textureRenderer, TextShaderProgram shaderProgram, RectangleVertexArrayObject vao, FrameBufferObject fbo) {
		this.textureRenderer = textureRenderer;
		this.shaderProgram = shaderProgram;
		this.vao = vao;
		this.fbo = fbo;
	}

	/**
	 * Renders text.
	 *
	 * @param x         the <code>x</code> offset of the text from the left side of the screen
	 * @param y         the <code>y</code> offset of the text from the top of the screen
	 * @param text      the {@link String} to display
	 * @param lineWidth the max width of each line of text in pixels, or 0 to indicate no wrapping
	 * @param font      the {@link GameFont} of the text
	 * @param fontSize  the size of the text
	 * @param colour    the colour of the text
	 *
	 * @return the number of lines of text rendered
	 */
	public int render(float x, float y, String text, float lineWidth, GameFont font, float fontSize, int colour) {
		return render(new Matrix4f().translate(x, y), text, lineWidth, font, fontSize, colour);
	}

	/**
	 * Renders text.
	 *
	 * @param transform the transformation matrix to be applied to the text at the end
	 * @param text      the text
	 * @param lineWidth the max width of each line of text in pixels, or 0 to indicate no wrapping
	 * @param font      the <code>GameFont</code> of the text
	 * @param fontSize  the font size
	 * @param colour    the {@link Colour} (int)
	 *
	 * @return the number of lines of text rendered
	 */
	public int render(Matrix4f transform, String text, float lineWidth, GameFont font, float fontSize, int colour) {
		fbo.bind(glContext);
		font.texture().bind(glContext);
		glClearColor(0, 0, 0, 0);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		shaderProgram.bind(glContext);
		shaderProgram.setInt("textureSampler", 0);
		shaderProgram.setFloat("texWidth", font.texture().width());
		shaderProgram.setFloat("texHeight", font.texture().height());
		shaderProgram.setColour("fill", colour);

		int numLines;
		if (lineWidth == 0) {
			renderOneLine(0, 0, text, font, fontSize);
			numLines = 1;
		} else {
			List<Pair<String, Float>> stringPairs = convertToStringPairs(text, font, fontSize, lineWidth);
			numLines = stringPairs.size();
			if (hAlign == ALIGN_LEFT) {
				for (int i = 0; i < stringPairs.size(); i++) {
					Pair<String, Float> pair = stringPairs.get(i);
					renderOneLine(0, i * fontSize, pair.a, font, fontSize);
				}
			} else if (hAlign == ALIGN_CENTER) {
				for (int i = 0; i < stringPairs.size(); i++) {
					Pair<String, Float> pair = stringPairs.get(i);
					renderOneLine((lineWidth - pair.b) * 0.5f, i * fontSize, pair.a, font, fontSize);
				}
			} else if (hAlign == ALIGN_RIGHT) {
				for (int i = 0; i < stringPairs.size(); i++) {
					Pair<String, Float> pair = stringPairs.get(i);
					renderOneLine(lineWidth - pair.b, i * fontSize, pair.a, font, fontSize);
				}
			}
		}
		FrameBufferObject.unbind(glContext);
		Texture tex = fbo.texture();
		Matrix4f m = new Matrix4f()
				.translate(-1, 1).scale(2 / glContext.width(), -2 / glContext.height())
				.multiply(transform);
		if (vAlign == ALIGN_CENTER) {
			m = m.translate(0, -numLines * fontSize / 2);
		} else if (vAlign == ALIGN_BOTTOM) {
			m = m.translate(0, -numLines * fontSize);
		}
		m = m.scale(tex.width(), tex.height());
		textureRenderer.render(tex, m);
		return numLines;
	}

	private void renderOneLine(float xOffset, float yOffset, String text, GameFont font, float fontSize) {
		char[] chars = text.toCharArray();
		Matrix4f transform = new Matrix4f().translate(-1, -1).scale(2, 2).scale(1 / glContext.width(), 1 / glContext.height());
		float sizeMultiplier = fontSize / font.getFontSize();
		fbo.bind(glContext);

		CharacterData[] characterDataArray = font.getCharacterDatas();
		for (char ch : chars) {
			CharacterData c = characterDataArray[ch];
			short xAdvance = c.xAdvance();
			if (ch == ' ') {
				xOffset += xAdvance * sizeMultiplier;
				continue;
			}
			Matrix4f copy = transform.copy()
					.translate(xOffset + c.xOffset() * sizeMultiplier, yOffset + c.yOffset() * sizeMultiplier)
					.scale(c.width() * sizeMultiplier, c.height() * sizeMultiplier);
			shaderProgram.setMat4("matrix4f", copy);
			shaderProgram.setFloat("width", c.width());
			shaderProgram.setFloat("height", c.height());
			shaderProgram.setFloat("x", c.x());
			shaderProgram.setFloat("y", c.y());
			vao.draw(glContext);
			xOffset += xAdvance * sizeMultiplier;
		}
	}

	private List<Pair<String, Float>> convertToStringPairs(String text, GameFont font, float fontSize, float lineWidth) {
		CharacterData[] characterDatas = font.getCharacterDatas();

		List<Pair<String, Float>> pairs = new ArrayList<>();

		String[] words = text.split(" ");
		float sizeMultiplier = fontSize / font.getFontSize();

		float currentStringWidth = 0;
		String currentString = "";
		for (String word : words) {
			float wordWidth = 0;
			for (int j = 0; j < word.length(); j++) {
				wordWidth += characterDatas[word.charAt(j)].xAdvance() * sizeMultiplier;
			}

			if (currentStringWidth + wordWidth <= lineWidth) {
				if (!currentString.isEmpty()) {
					currentString += " ";
					currentStringWidth += characterDatas[' '].xAdvance() * sizeMultiplier;
				}
				currentStringWidth += wordWidth;
				currentString += word;
			} else {
				pairs.add(new Pair<>(currentString.trim(), currentStringWidth));
				currentStringWidth = wordWidth;
				currentString = word;
			}
		}
		pairs.add(new Pair<>(currentString.trim(), currentStringWidth));
		return pairs;
	}

	public void alignLeft() {
		hAlign = ALIGN_LEFT;
	}

	public void alignCenterHorizontal() {
		hAlign = ALIGN_CENTER;
	}

	public void alignRight() {
		hAlign = ALIGN_RIGHT;
	}

	public void alignTop() {
		vAlign = ALIGN_TOP;
	}

	public void alignCenterVertical() {
		vAlign = ALIGN_CENTER;
	}

	public void alignBottom() {
		vAlign = ALIGN_BOTTOM;
	}

	public int hAlign() {
		return hAlign;
	}

	public int vAlign() {
		return vAlign;
	}

	public void setHorizontalAlign(int hAlign) {
		this.hAlign = hAlign;
		if (hAlign != ALIGN_LEFT && hAlign != ALIGN_CENTER && hAlign != ALIGN_RIGHT) {
			throw new IllegalArgumentException("Invalid horizontal alignment: " + hAlign + ".\n" +
					"Expected ALIGN_LEFT (" + ALIGN_LEFT + "), ALIGN_CENTER (" + ALIGN_CENTER + "), or ALIGN_RIGHT (" + ALIGN_RIGHT + ")");
		}
	}

	public void setVerticalAlign(int vAlign) {
		this.vAlign = vAlign;
		if (vAlign != ALIGN_TOP && vAlign != ALIGN_CENTER && vAlign != ALIGN_BOTTOM) {
			throw new IllegalArgumentException("Invalid vertical alignment: " + vAlign + ".\n" +
					"Expected ALIGN_TOP (" + ALIGN_TOP + "), ALIGN_CENTER (" + ALIGN_CENTER + "), or ALIGN_BOTTOM (" + ALIGN_BOTTOM + ")");
		}
	}

}
