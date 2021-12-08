package context.visuals.renderer;

import common.math.Matrix4f;
import common.math.Vector2f;
import context.GLContext;
import context.GameContext;
import context.ResourcePack;
import context.visuals.builtin.RectangleVertexArrayObject;
import context.visuals.builtin.TextureShaderProgram;
import context.visuals.gui.RootGui;
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
	 * 
	 * @param glContext         the {@link GLContext}
	 * @param rootGuiDimensions a {@link Vector2f} representing the dimensions of
	 *                          the {@link RootGui}
	 * @param texture           the {@link Texture} to render
	 * @param centerX           the x position in pixels of the center of the
	 *                          texture
	 * @param centerY           the y position in pixels of the center of the
	 *                          texture
	 * @param scale             the scale of the texture
	 */
	public void render(GLContext glContext, Vector2f rootGuiDimensions, Texture texture, float centerX, float centerY, float scale) {
		Matrix4f matrix4f = new Matrix4f();
		matrix4f.translate(-1, 1).scale(2, -2).scale(1 / rootGuiDimensions.x, 1 / rootGuiDimensions.y)
				.translate(centerX, centerY).scale(texture.width() * scale, texture.height() * scale).translate(-0.5f, -0.5f);
		shaderProgram.bind(glContext);
		texture.bind(glContext);
		shaderProgram.setMat4("matrix4f", matrix4f);
		shaderProgram.setInt("textureSampler", texture.getTextureUnit());
		vao.draw(glContext);
	}

	/**
	 * Renders a texture in pixel coordinates.
	 * 
	 * @param glContext the {@link GLContext}
	 * @param rootGui   the {@link RootGui}
	 * @param texture   the {@link Texture} to render
	 * @param x         the x position in pixels of the top left corner of the
	 *                  texture
	 * @param y         the y position in pixels of the top left corner of the
	 *                  texture
	 * @param w         the width in pixels
	 * @param h         the height in pixels
	 */
	public void render(GLContext glContext, RootGui rootGui, Texture texture, float x, float y, float w, float h) {
		Vector2f rootGuiDimensions = rootGui.dimensions();
		Matrix4f matrix4f = new Matrix4f();
		matrix4f.translate(-1, 1).scale(2, -2).scale(1 / rootGuiDimensions.x, 1 / rootGuiDimensions.y)
				.translate(x, y).scale(w, h);
		render(glContext, texture, matrix4f);
	}

	/**
	 * Renders a texture using a transformation matrix.
	 * 
	 * @param glContext the {@link GLContext}
	 * @param vao       the {@link VertexArrayObject} to use
	 * @param texture   the texture to render
	 * @param matrix4f  the transformation matrix
	 */
	public void render(GLContext glContext, Texture texture, Matrix4f matrix4f) {
		shaderProgram.bind(glContext);
		texture.bind(glContext);
		shaderProgram.setMat4("matrix4f", matrix4f);
		shaderProgram.setInt("textureSampler", texture.getTextureUnit());
		vao.draw(glContext);
	}

	/**
	 * Renders a texture using a transformation matrix and diffuse colour.
	 * 
	 * @param glContext the {@link GLContext}
	 * @param vao       the {@link VertexArrayObject} to use
	 * @param texture   the texture to render
	 * @param matrix4f  the transformation matrix
	 * @param diffuse   the diffuse colour
	 */
	public void render(GLContext glContext, Texture texture, Matrix4f matrix4f, int diffuse) {
		shaderProgram.bind(glContext);
		texture.bind(glContext);
		shaderProgram.setMat4("matrix4f", matrix4f);
		shaderProgram.setInt("textureSampler", texture.getTextureUnit());
		shaderProgram.setColour("diffuse", diffuse);
		vao.draw(glContext);
		shaderProgram.setColour("diffuse", -1);
	}

}
