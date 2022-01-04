package context.visuals.renderer;

import common.math.Matrix4f;
import context.GameContext;
import context.ResourcePack;
import context.visuals.builtin.RectangleVertexArrayObject;
import context.visuals.builtin.TextureShaderProgram;
import context.visuals.lwjgl.ShaderProgram;
import context.visuals.lwjgl.Texture;
import context.visuals.lwjgl.VertexArrayObject;

/**
 * A {@link GameRenderer} that renders textures.
 * 
 * @author Jay
 *
 */
public class TextureRenderer extends GameRenderer {

	/**
	 * The {@link ShaderProgram} to use when rendering textures.
	 */
	private ShaderProgram shaderProgram;
	private RectangleVertexArrayObject vao;
	private int diffuse = -1;

	/**
	 * Creates a <code>TextureRenderer</code> using the texture shader program from
	 * a {@link GameContext}'s {@link ResourcePack}. The texture shader program
	 * should be put into the <code>ResourcePack</code> with the name
	 * <code>"texture"</code>.
	 * 
	 * @param context the <code>GameContext</code>
	 */
	public TextureRenderer(TextureShaderProgram shaderProgram, RectangleVertexArrayObject vao) {
		this.shaderProgram = shaderProgram;
		this.vao = vao;
	}

	/**
	 * Renders a texture with default proportions in pixel coordinates.
	 * @param texture   the {@link Texture} to render
	 * @param centerX   the x position in pixels of the center of the texture
	 * @param centerY   the y position in pixels of the center of the texture
	 * @param scale     the scale of the texture
	 */
	public void render(Texture texture, float centerX, float centerY, float scale) {
		Matrix4f matrix4f = new Matrix4f();
		matrix4f.translate(-1, 1).scale(2, -2).scale(1 / glContext.width(), 1 / glContext.height())
				.translate(centerX, centerY).scale(texture.width() * scale, texture.height() * scale).translate(-0.5f, -0.5f);
		shaderProgram.bind(glContext);
		texture.bind(glContext, 0);
		shaderProgram.setMat4("matrix4f", matrix4f);
		shaderProgram.setInt("textureSampler", 0);
		shaderProgram.setColour("diffuse", diffuse);
		vao.draw(glContext);
	}

	/**
	 * Renders a texture with default proportions in pixel coordinates.
	 * @param texture   the {@link Texture} to render
	 * @param centerX   the x position in pixels of the center of the texture
	 * @param centerY   the y position in pixels of the center of the texture
	 * @param depth     the depth of the texture
	 * @param scale     the scale of the texture
	 */
	public void renderDepth(Texture texture, float centerX, float centerY, float depth, float scale) {
		Matrix4f matrix4f = new Matrix4f();
		matrix4f.translate(-1, 1, depth).scale(2, -2).scale(1 / glContext.width(), 1 / glContext.height())
				.translate(centerX, centerY).scale(texture.width() * scale, texture.height() * scale).translate(-0.5f, -0.5f);
		shaderProgram.bind(glContext);
		texture.bind(glContext, 0);
		shaderProgram.setMat4("matrix4f", matrix4f);
		shaderProgram.setInt("textureSampler", 0);
		shaderProgram.setColour("diffuse", diffuse);
		vao.draw(glContext);
	}

	/**
	 * Renders a texture in pixel coordinates.
	 * @param texture   the {@link Texture} to render
	 * @param x         the x position in pixels of the top left corner of the
	 *                  texture
	 * @param y         the y position in pixels of the top left corner of the
	 *                  texture
	 * @param w         the width in pixels
	 * @param h         the height in pixels
	 */
	public void render(Texture texture, float x, float y, float w, float h) {
		Matrix4f matrix4f = new Matrix4f();
		matrix4f.translate(-1, 1).scale(2, -2).scale(1 / glContext.width(), 1 / glContext.height())
				.translate(x, y).scale(w, h);
		render(texture, matrix4f);
	}

	/**
	 * Renders a texture using a transformation matrix.
	 * @param texture   the texture to render
	 * @param matrix4f  the transformation matrix
	 * @param vao       the {@link VertexArrayObject} to use
	 */
	public void render(Texture texture, Matrix4f matrix4f) {
		shaderProgram.bind(glContext);
		texture.bind(glContext, 0);
		shaderProgram.setMat4("matrix4f", matrix4f);
		shaderProgram.setInt("textureSampler", 0);
		shaderProgram.setColour("diffuse", diffuse);
		vao.draw(glContext);
	}

	public int getDiffuse() {
		return diffuse;
	}

	public void setDiffuse(int diffuse) {
		this.diffuse = diffuse;
	}

	public void resetDiffuse() {
		diffuse = -1;
	}

}
